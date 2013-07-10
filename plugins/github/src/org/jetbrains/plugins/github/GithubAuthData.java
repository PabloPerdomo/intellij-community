/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.plugins.github;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Container for authentication data: host, login and password.
 *
 * @author Aleksey Pivovarov
 */
public class GithubAuthData {
  public enum AuthType {BASIC, TOKEN, ANONYMOUS}

  private final AuthType myAuthType;
  private @NotNull final String myHost;
  private @NotNull final String myLogin;
  private @Nullable final BasicAuth myBasicAuth;
  private @Nullable final TokenAuth myTokenAuth;

  private GithubAuthData(@NotNull AuthType authType,
                         @NotNull String host,
                         @NotNull String login,
                         @Nullable BasicAuth basicAuth,
                         @Nullable TokenAuth tokenAuth) {
    myAuthType = authType;
    myHost = host;
    myLogin = login;
    myBasicAuth = basicAuth;
    myTokenAuth = tokenAuth;
  }

  public static GithubAuthData createAnonymous() {
    return createAnonymous(GithubApiUtil.DEFAULT_GITHUB_HOST, "");
  }

  public static GithubAuthData createAnonymous(@NotNull String host) {
    return createAnonymous(host, "");
  }

  public static GithubAuthData createAnonymous(@NotNull String host, @NotNull String login) {
    return new GithubAuthData(AuthType.ANONYMOUS, host, login, null, null);
  }

  public static GithubAuthData createBasicAuth(@NotNull String host, @NotNull String login, @NotNull String password) {
    return new GithubAuthData(AuthType.BASIC, host, login, new BasicAuth(login, password), null);
  }

  public static GithubAuthData createTokenAuth(@NotNull String host, @NotNull String login, @NotNull String token) {
    return new GithubAuthData(AuthType.TOKEN, host, login, null, new TokenAuth(token));
  }

  @NotNull
  public AuthType getAuthType() {
    return myAuthType;
  }

  @NotNull
  public String getHost() {
    return myHost;
  }

  @NotNull
  public String getLogin() {
    return myLogin;
  }

  @Nullable
  public BasicAuth getBasicAuth() {
    return myBasicAuth;
  }

  @Nullable
  public TokenAuth getTokenAuth() {
    return myTokenAuth;
  }

  public static class BasicAuth {
    private @NotNull final String myLogin;
    private @NotNull final String myPassword;

    private BasicAuth(@NotNull String login, @NotNull String password) {
      myLogin = login;
      myPassword = password;
    }

    @NotNull
    public String getLogin() {
      return myLogin;
    }

    @NotNull
    public String getPassword() {
      return myPassword;
    }
  }

  public static class TokenAuth {
    private @NotNull final String myToken;

    private TokenAuth(@NotNull String token) {
      myToken = token;
    }

    @NotNull
    public String getToken() {
      return myToken;
    }
  }
}
