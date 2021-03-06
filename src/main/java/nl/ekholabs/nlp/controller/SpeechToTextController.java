package nl.ekholabs.nlp.controller;

import java.io.IOException;

import nl.ekholabs.nlp.client.ElsieDeetectFeignClient;
import nl.ekholabs.nlp.model.Language;
import nl.ekholabs.nlp.model.TextResponse;
import nl.ekholabs.nlp.service.SpeechToTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
public class SpeechToTextController {

  private final SpeechToTextService speechToTextService;
  private final ElsieDeetectFeignClient elsieDeetect;

  @Autowired
  public SpeechToTextController(final SpeechToTextService speechToTextService, final ElsieDeetectFeignClient elsieDeetect) {
    this.speechToTextService = speechToTextService;
    this.elsieDeetect = elsieDeetect;
  }

  @PostMapping(path = "/speechToText", produces = APPLICATION_JSON_UTF8_VALUE, consumes = MULTIPART_FORM_DATA_VALUE)
  public TextResponse speechToText(final @RequestParam(value = "audio") MultipartFile audioFile) throws IOException {

    final String outputText = speechToTextService.processSpeech(audioFile.getBytes());
    return new TextResponse(outputText);
  }

  @PostMapping(path = "/idLanguage", produces = APPLICATION_JSON_UTF8_VALUE, consumes = TEXT_PLAIN_VALUE)
  public Language idLanguage(final @RequestBody String text) throws IOException {
    return elsieDeetect.idLanguage(text);
  }
}
