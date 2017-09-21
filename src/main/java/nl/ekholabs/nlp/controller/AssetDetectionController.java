package nl.ekholabs.nlp.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import nl.ekholabs.nlp.client.ElsieDeeCreateAssetFeignClient;
import nl.ekholabs.nlp.client.ElsieDeeSearchAssetsFeignClient;
import nl.ekholabs.nlp.client.StreamServicesFeignClient;
import nl.ekholabs.nlp.model.Asset;
import nl.ekholabs.nlp.model.AssetDetails;
import nl.ekholabs.nlp.model.AssetSearchRequest;
import nl.ekholabs.nlp.model.Language;
import nl.ekholabs.nlp.model.StreamDetails;
import nl.ekholabs.nlp.model.Streams;
import nl.ekholabs.nlp.model.Subtitles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AssetDetectionController {

  private final static Logger LOGGER = LoggerFactory.getLogger(AssetDetectionController.class);

  private final StreamServicesFeignClient streamServicesFeignClient;
  private final ElsieDeeCreateAssetFeignClient elsieDeeCreateAssetFeignClient;
  private final ElsieDeeSearchAssetsFeignClient elsieDeeSearchAssetsFeignClient;

  @Autowired
  public AssetDetectionController(final StreamServicesFeignClient streamServicesFeignClient,
                                  final ElsieDeeCreateAssetFeignClient elsieDeeCreateAssetFeignClient,
                                  final ElsieDeeSearchAssetsFeignClient elsieDeeSearchAssetsFeignClient) {
    this.streamServicesFeignClient = streamServicesFeignClient;
    this.elsieDeeCreateAssetFeignClient = elsieDeeCreateAssetFeignClient;
    this.elsieDeeSearchAssetsFeignClient = elsieDeeSearchAssetsFeignClient;
  }

  @PostMapping(path = "/identifyAsset", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
  public List<Asset> identifyAsset(final @RequestBody AssetSearchRequest assetSearchRequest) throws IOException {

    final Streams streams = streamServicesFeignClient.streamDetails(assetSearchRequest.getUrl());

    final Stream<StreamDetails> streamsByLanguage = streams.getStreams().stream()
        .filter(streamDetails -> {
          final Language searchedLanguage = assetSearchRequest.getLanguage();
          final String foundLanguage = streamDetails.getLanguage();
          return foundLanguage.equalsIgnoreCase(searchedLanguage.getCode());
        });

    final Subtitles subtitles = streamServicesFeignClient.extractSubtitles(streamsByLanguage);

    final AssetDetails assetDetails = assetSearchRequest.getAssetDetails();
    final Asset asset = elsieDeeCreateAssetFeignClient.createAsset(assetDetails.getAssetTitle(), subtitles);
    LOGGER.info("Asset created: {}", asset);

    return elsieDeeSearchAssetsFeignClient.assets(assetDetails);
  }
}
