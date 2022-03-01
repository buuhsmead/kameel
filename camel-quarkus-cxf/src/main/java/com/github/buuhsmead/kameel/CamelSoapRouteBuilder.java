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
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class CamelSoapRouteBuilder extends RouteBuilder {


    @Inject
    NumberConversionClient numberConversionClient;

    @Override
    public void configure() throws Exception {

        from("direct:soapCalling")
                .setHeader("endpoint", constant("https://www.dataaccess.com/webservicesserver/NumberConversion.wso?WSDL"))
                .setHeader("SOAPAction", constant("NumberToDollars"))
                .bean(numberConversionClient, "callIt")
                        .to("log:elp");


//        from("direct:soapCalling")
//                .setBody(constant("12345"))
//                .bean(NumberConversion.class)
//
//                .setHeader(CxfConstants.OPERATION_NAME, constant("NumberToDollars"))
//                .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://www.dataaccess.com/webservicesserver/"))
//
//                // Invoke our test service using CXF
//                .to("cxf://http://localhost:8423/test/BookService"
//                        + "?serviceClass=com.dataaccess.webservicesserver.NumberConversion"
//                        + "&portName={http://www.dataaccess.com/webservicesserver/}NumberConversionSoap"
//                        + "&endpointName={http://www.dataaccess.com/webservicesserver/}NumberConversionSoapType"
//                        + "&wsdlURL=wsdl/NumberConversion.wsdl")
//                .log("The title is: ${body[0].book.title}");
    }
}
