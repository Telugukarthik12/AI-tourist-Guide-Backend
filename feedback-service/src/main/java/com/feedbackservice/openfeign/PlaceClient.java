package com.feedbackservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PLACE-SERVICE")
public interface PlaceClient {

	@GetMapping("place/getplacefortour/{placeId}")
	public Integer getPlacefortour(@PathVariable(name="placeId") Integer placeId);

}
