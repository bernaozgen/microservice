package com.kodlamaio.inventoryService.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.model.ModelUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.common.utilities.results.DataResult;
import com.kodlamaio.common.utilities.results.Result;
import com.kodlamaio.common.utilities.results.SuccessDataResult;
import com.kodlamaio.common.utilities.results.SuccessResult;
import com.kodlamaio.inventoryService.business.abstracts.BrandService;
import com.kodlamaio.inventoryService.business.abstracts.ModelService;
import com.kodlamaio.inventoryService.business.constants.Messages;
import com.kodlamaio.inventoryService.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryService.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryService.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetAllModelResponse;
import com.kodlamaio.inventoryService.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryService.business.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryService.dataAccess.ModelRepository;
import com.kodlamaio.inventoryService.entites.Model;
import com.kodlamaio.inventoryService.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;
	private InventoryProducer inventoryProducer;
	private BrandService brandServise;

	@Override
	public DataResult<List<GetAllModelResponse>> getAll() {
		List<Model> models = this.modelRepository.findAll();

		List<GetAllModelResponse> response = models.stream()
				.map(model -> this.modelMapperService.forResponse().map(model, GetAllModelResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetAllModelResponse>>(response, Messages.ModelListed);
	}

	@Override
	public DataResult<CreateModelResponse> add(CreateModelRequest createModelRequest) {
		checkIfBrandExistsById(createModelRequest.getBrandId());
		Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);
		model.setId(UUID.randomUUID().toString());

		this.modelRepository.save(model);

		CreateModelResponse createModelResponse = this.modelMapperService.forResponse().map(model,
				CreateModelResponse.class);
		return new SuccessDataResult<CreateModelResponse>(createModelResponse, Messages.ModelCreated);
	}

	@Override
	public Result delete(String id) {
		checkIfModelExistsById(id);
		this.modelRepository.deleteById(id);
		return new SuccessResult(Messages.ModelDeleted);
	}

	@Override
	public DataResult<UpdateModelResponse> update(UpdateModelRequest updateModelRequest) {
		checkIfModelExistsById(updateModelRequest.getId());
		checkIfBrandExistsById(updateModelRequest.getBrandId());
		Model model = this.modelMapperService.forRequest().map(updateModelRequest, Model.class);

		Model updateModel = this.modelRepository.save(model);
		ModelUpdatedEvent modelUpdatedEvent = new ModelUpdatedEvent();
		modelUpdatedEvent.setBrandId(updateModel.getBrand().getId());
		modelUpdatedEvent.setBrandName(updateModel.getBrand().getName());
		modelUpdatedEvent.setModelId(updateModel.getId());
		modelUpdatedEvent.setModelName(updateModel.getName());
		modelUpdatedEvent.setMessage("Model updated");

		this.inventoryProducer.sendMessage(modelUpdatedEvent);

		UpdateModelResponse updateModelResponse = this.modelMapperService.forResponse().map(model,
				UpdateModelResponse.class);
		return new SuccessDataResult<UpdateModelResponse>(updateModelResponse, Messages.ModelUpdated);
	}
	@Override
	public DataResult<GetModelResponse> getById(String id) {
		checkIfModelExistsById(id);
		Model model = this.modelRepository.findById(id).get();
		GetModelResponse getModelResponse = this.modelMapperService.forResponse().map(model, GetModelResponse.class);
		return new SuccessDataResult<GetModelResponse>(getModelResponse, Messages.ModelListed);
	}

	private void checkIfModelExistsById(String id) {
		var result = this.modelRepository.findById(id);
		if (result == null) {
			throw new BusinessException(Messages.ModelIdNotFound);
		}
	}

	private void checkIfBrandExistsById(String brandId) {
		var result = this.brandServise.getById(brandId);
		if (result == null) {
			throw new BusinessException(Messages.BrandIdNotFound);
		}
	}



}