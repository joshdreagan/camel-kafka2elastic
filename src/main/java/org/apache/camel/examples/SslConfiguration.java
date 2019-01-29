/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.examples;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.net.ssl.SSLContext;
import org.apache.camel.CamelContext;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SslConfiguration {
  
  @Bean
  KeyStoreParameters trustStore(SslProperties sslProperties, CamelContext camelContext) {
    KeyStoreParameters trustStore = new KeyStoreParameters();
    trustStore.setCamelContext(camelContext);
    trustStore.setResource(sslProperties.getTruststorePath());
    trustStore.setPassword(sslProperties.getTruststorePassword());
    return trustStore;
  }
  
  @Bean
  TrustManagersParameters trustManagers(KeyStoreParameters trustStore) {
    TrustManagersParameters trustManagers = new TrustManagersParameters();
    trustManagers.setKeyStore(trustStore);
    return trustManagers;
  }
  
  @Bean
  SSLContextParameters sslContextParameters(SslProperties sslProperties, TrustManagersParameters trustManagers) {
    SSLContextParameters sslContextParameters = new SSLContextParameters();
    sslContextParameters.setTrustManagers(trustManagers);
    return sslContextParameters;
  }
  
  @Bean
  SSLContext sslContext(CamelContext camelContext, SSLContextParameters sslContextParameters) throws GeneralSecurityException, IOException {
    SSLContext sslContext = sslContextParameters.createSSLContext(camelContext);
    return sslContext;
  }
}
