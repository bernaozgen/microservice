package com.kodlamaio.invantoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invantoryServer.business.abstracts.ModelService;
import com.kodlamaio.invantoryServer.business.requests.create.CreateModelRequest;
import com.kodlamaio.invantoryServer.business.requests.update.UpdateModelRequest;
import com.kodlamaio.invantoryServer.business.responses.create.CreateModelResponse;
import com.kodlamaio.invantoryServer.business.responses.get.GetAllModelResponse;
import com.kodlamaio.invantoryServer.business.responses.update.UpdateModelResponse;
import com.kodlamaio.invantoryServer.dataAccess.ModelRepository;
import com.kodlamaio.invantoryServer.entites.Model;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;

	@Override
	public List<GetAllModelResponse> getAll() {
		List<Model> models = this.modelRepository.findAll();

		List<GetAllModelResponse> response = models.stream()
				.map(model -> this.modelMapperService.forResponse().map(model, GetAllModelResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateModelResponse add(CreateModelRequest createModelRequest) {
		checkIfBrandExistsById(createModelRequest.getBrandId());
		Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);
		model.setId(UUID.randomUUID().toString());

		this.modelRepository.save(model);

		CreateModelResponse createBrandResponse = this.modelMapperService.forResponse().map(model,
				CreateModelResponse.class);
		return createBrandResponse;
	}

	@Override
	public void delete(String id) {
		checkIfModelExistsById(id);
		this.modelRepository.deleteById(id);

	}

	@Override
	public UpdateModelResponse update(UpdateModelRequest updateModelRequest) {
		checkIfModelExistsById(updateModelRequest.getId());
		checkIfBrandExistsById(updateModelRequest.getBrandId());
		Model model = this.modelMapperService.forRequest().map(updateModelRequest, Model.class);
		this.modelRepository.save(model);
		UpdateModelResponse updateModelResponse = this.modelMapperService.forResponse().map(model,
				UpdateModelResponse.class);
		return updateModelResponse;
	}

	private void checkIfModelExistsById(String id) {
		var result = this.modelRepository.findById(id);
		if (result == null) {
			throw new BusinessException("MODEL.NOT.EXİSTS");
		}
	}

	private void checkIfBrandExistsById(String brandId) {
		var result = this.modelRepository.findById(brandId);
		if (result == null) {
			throw new BusinessException("BRAND.ID.NOT.EXİSTS");
		}
	}
	

}