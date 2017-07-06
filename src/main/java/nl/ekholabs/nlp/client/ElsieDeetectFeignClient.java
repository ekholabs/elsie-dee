package nl.ekholabs.nlp.client;

import nl.ekholabs.nlp.model.Language;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(serviceId = "elsie-deetect")
public interface ElsieDeetectFeignClient {

  @PostMapping(path = "elsie-deetect/identify", produces = APPLICATION_JSON_UTF8_VALUE)
  Language identify(@RequestBody final String text);

}
