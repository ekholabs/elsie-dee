package nl.ekholabs.nlp.client;

import java.util.List;

import nl.ekholabs.nlp.client.configuration.JsonSupportConfig;
import nl.ekholabs.nlp.model.StreamDetails;
import nl.ekholabs.nlp.model.Streams;
import nl.ekholabs.nlp.model.Subtitles;
import nl.ekholabs.nlp.model.VideoUrl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@FeignClient(serviceId = "stream-services", configuration = JsonSupportConfig.class)
public interface StreamServicesFeignClient {

  @PostMapping(path = "/ffmpeg/streamDetails",
      consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  Streams streamDetails(@RequestBody final VideoUrl videoUrl);

  @PostMapping(path = "/ffmpeg/extractSubtitles",
      consumes = APPLICATION_JSON_UTF8_VALUE,
      produces = APPLICATION_JSON_UTF8_VALUE)
  Subtitles extractSubtitles(@RequestBody final List<StreamDetails> streamsByLanguage);
}