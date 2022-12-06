package Spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import utils.config;
import java.io.IOException;

public class SpotifyArtistCommand {
    public String SpotifyArtist(String track_info) throws IOException, ParseException, SpotifyWebApiException {

        String[] artist = track_info.substring(track_info.indexOf("id=") + 3).trim().split(",");
        String artistID = artist[0];

        config obj = new config();

        final String clientId = obj.SpotifyclientId;
        final String clientSecret = obj.SpotifyclientSecret;
        final String refreshToken = obj.SpotifyrefreshToken;

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();
        final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            //System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return "null";
        }

        final GetArtistRequest getArtistRequest = spotifyApi.getArtist(artistID)
                .build();

        try {
            final Artist artistName = getArtistRequest.execute();
            return artistName.getName();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return "null";
        }
    }
}

