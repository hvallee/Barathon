/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-01-08 17:48:37 UTC)
 * on 2016-02-03 at 16:32:13 UTC 
 * Modify at your own risk.
 */

package barathon.backend.bar.apiBar;

/**
 * Service definition for ApiBar (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link ApiBarRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class ApiBar extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.21.0 of the apiBar library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "apiBar/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public ApiBar(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  ApiBar(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "bar".
   *
   * This request holds the parameters needed by the apiBar server.  After setting any optional
   * parameters, call the {@link Bar#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public Bar bar() throws java.io.IOException {
    Bar result = new Bar();
    initialize(result);
    return result;
  }

  public class Bar extends ApiBarRequest<barathon.backend.bar.apiBar.model.Bar> {

    private static final String REST_PATH = "bar";

    /**
     * Create a request for the method "bar".
     *
     * This request holds the parameters needed by the the apiBar server.  After setting any optional
     * parameters, call the {@link Bar#execute()} method to invoke the remote operation. <p> {@link
     * Bar#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must be
     * called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected Bar() {
      super(ApiBar.this, "GET", REST_PATH, null, barathon.backend.bar.apiBar.model.Bar.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public Bar setAlt(java.lang.String alt) {
      return (Bar) super.setAlt(alt);
    }

    @Override
    public Bar setFields(java.lang.String fields) {
      return (Bar) super.setFields(fields);
    }

    @Override
    public Bar setKey(java.lang.String key) {
      return (Bar) super.setKey(key);
    }

    @Override
    public Bar setOauthToken(java.lang.String oauthToken) {
      return (Bar) super.setOauthToken(oauthToken);
    }

    @Override
    public Bar setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Bar) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Bar setQuotaUser(java.lang.String quotaUser) {
      return (Bar) super.setQuotaUser(quotaUser);
    }

    @Override
    public Bar setUserIp(java.lang.String userIp) {
      return (Bar) super.setUserIp(userIp);
    }

    @Override
    public Bar set(String parameterName, Object value) {
      return (Bar) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "bars".
   *
   * This request holds the parameters needed by the apiBar server.  After setting any optional
   * parameters, call the {@link Bars#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public Bars bars() throws java.io.IOException {
    Bars result = new Bars();
    initialize(result);
    return result;
  }

  public class Bars extends ApiBarRequest<barathon.backend.bar.apiBar.model.BarCollection> {

    private static final String REST_PATH = "barcollection";

    /**
     * Create a request for the method "bars".
     *
     * This request holds the parameters needed by the the apiBar server.  After setting any optional
     * parameters, call the {@link Bars#execute()} method to invoke the remote operation. <p> {@link
     * Bars#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must be
     * called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected Bars() {
      super(ApiBar.this, "GET", REST_PATH, null, barathon.backend.bar.apiBar.model.BarCollection.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public Bars setAlt(java.lang.String alt) {
      return (Bars) super.setAlt(alt);
    }

    @Override
    public Bars setFields(java.lang.String fields) {
      return (Bars) super.setFields(fields);
    }

    @Override
    public Bars setKey(java.lang.String key) {
      return (Bars) super.setKey(key);
    }

    @Override
    public Bars setOauthToken(java.lang.String oauthToken) {
      return (Bars) super.setOauthToken(oauthToken);
    }

    @Override
    public Bars setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Bars) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Bars setQuotaUser(java.lang.String quotaUser) {
      return (Bars) super.setQuotaUser(quotaUser);
    }

    @Override
    public Bars setUserIp(java.lang.String userIp) {
      return (Bars) super.setUserIp(userIp);
    }

    @Override
    public Bars set(String parameterName, Object value) {
      return (Bars) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "majbase".
   *
   * This request holds the parameters needed by the apiBar server.  After setting any optional
   * parameters, call the {@link Majbase#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public Majbase majbase() throws java.io.IOException {
    Majbase result = new Majbase();
    initialize(result);
    return result;
  }

  public class Majbase extends ApiBarRequest<Void> {

    private static final String REST_PATH = "miseAjour";

    /**
     * Create a request for the method "majbase".
     *
     * This request holds the parameters needed by the the apiBar server.  After setting any optional
     * parameters, call the {@link Majbase#execute()} method to invoke the remote operation. <p>
     * {@link
     * Majbase#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected Majbase() {
      super(ApiBar.this, "POST", REST_PATH, null, Void.class);
    }

    @Override
    public Majbase setAlt(java.lang.String alt) {
      return (Majbase) super.setAlt(alt);
    }

    @Override
    public Majbase setFields(java.lang.String fields) {
      return (Majbase) super.setFields(fields);
    }

    @Override
    public Majbase setKey(java.lang.String key) {
      return (Majbase) super.setKey(key);
    }

    @Override
    public Majbase setOauthToken(java.lang.String oauthToken) {
      return (Majbase) super.setOauthToken(oauthToken);
    }

    @Override
    public Majbase setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Majbase) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Majbase setQuotaUser(java.lang.String quotaUser) {
      return (Majbase) super.setQuotaUser(quotaUser);
    }

    @Override
    public Majbase setUserIp(java.lang.String userIp) {
      return (Majbase) super.setUserIp(userIp);
    }

    @Override
    public Majbase set(String parameterName, Object value) {
      return (Majbase) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link ApiBar}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link ApiBar}. */
    @Override
    public ApiBar build() {
      return new ApiBar(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link ApiBarRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setApiBarRequestInitializer(
        ApiBarRequestInitializer apibarRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(apibarRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
