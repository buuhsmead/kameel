/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.buuhsmead.kameel;


import com.dataaccess.webservicesserver.NumberConversion;
import com.dataaccess.webservicesserver.NumberConversionSoapType;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class NumberConversionClient {
    // endpointAddress = "https://security-sign.api.stst.intern.folksam.se:443/security/sign-1.0"

    public String callIt(@Header("endpoint") String endpointAddress, @Body String userKey) {

//        NumberConversion service = new NumberConversion();
//        NumberConversionSoapType port = service.getNumberConversionSoap();
//        ((BindingProvider)port).getBinding().

//        ClientProxyFactoryBean factory

//        ((HTTPConduit) ClientProxy.getClient(port).getConduit());

//        String dollars = port.numberToDollars(BigDecimal.valueOf(1));

// https://cxf.apache.org/docs/client-http-transport-including-ssl-support.html#ClientHTTPTransport(includingSSLsupport)-Howtooverridetheserviceaddress?
        ClientProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        factory.setServiceClass(NumberConversionSoapType.class);

        factory.setAddress(endpointAddress);

        NumberConversionSoapType numberConversionPort = (NumberConversionSoapType) factory.create();

        Client client = ClientProxy.getClient(numberConversionPort);



        Map<String, List<String>> headers = new HashMap<>();
        headers.put("user-key", Arrays.asList(userKey));

        client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);

        client.getOutInterceptors().add(new LoggingOutInterceptor());
        client.getInInterceptors().add(new LoggingInInterceptor());

        String response = null;
        try {
            response = numberConversionPort.numberToDollars(BigDecimal.valueOf(12));

            System.out.println("NumberConversion SOAP called.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }
}

