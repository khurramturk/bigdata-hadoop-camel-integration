package com.ews.enterprise.datacollection.camel;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.processor.interceptor.DefaultTraceEventMessage;
import org.apache.camel.processor.interceptor.TraceEventMessage;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.datacollection.DataCollectionService;
import com.ews.enterprise.datacollection.guice.DataCollectionJpaModule;
import com.ews.enterprise.datacollection.guice.DataCollectionMongoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TraceMessageProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraceMessageProcessor.class);
    private static ConcurrentHashMap<String, Date> msgCache = new ConcurrentHashMap<String, Date>();
    private static DataCollectionService dcs;

    // resolve data collection setup at static level
    static {
        FileInputStream fis = null;
        try {
            // load properties
            Properties props = new Properties();
            fis = new FileInputStream("environment.properties");
            props.load(fis);
            fis = new FileInputStream("application.properties");
            props.load(fis);
            // check for camel data collection
            String camelDcOnString = props.getProperty("org.airedale.dc.camel");
            String camelDcApproachString = props
                .getProperty("org.airedale.dc.impl");
            if ("ON".equals(camelDcOnString)) {
                // create an injector based on approach
                Injector injector = null;
                if ("MONGO".equals(camelDcApproachString)) {
                    injector = Guice.createInjector(
                        new DataCollectionMongoModule(props));
                }
                if ("JPA".equals(camelDcApproachString)) {
                    injector = Guice.createInjector(
                        new DataCollectionJpaModule(props));
                }

                // create an instance of the data collection service and cache
                // it
                dcs = injector.getInstance(DataCollectionService.class);
            }
        } catch (Exception e) {
            LOGGER.error("failed to initialize AiredaleTraceMessageProcessor",
                e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    public final void process(final Exchange wrapperExchange) throws Exception {

        // get the real exchange
        ConcurrentHashMap wchm = (ConcurrentHashMap) wrapperExchange
            .getProperties();
        Exchange exchange = (Exchange) wchm.get(Exchange.TRACE_EVENT_EXCHANGE);
        // get headers to get fileid
        HashMap headerHm = (HashMap) exchange.getIn().getHeaders();
        String fileId = (String) headerHm.get("CamelExternalFileID");

        // get the rest of the info
        TraceEventMessage msg = wrapperExchange.getIn().getBody(
            DefaultTraceEventMessage.class);
        String fromEndpoint = msg.getFromEndpointUri();
        String toNode = msg.getToNode();
        String exchangeId = msg.getExchangeId();
        Date currentTs = msg.getTimestamp();

        // construct key to see if we've seen before
        String key = fromEndpoint + toNode + exchangeId;
        Date previousTs = msgCache.get(key);

        // if we have
        /*
        if (previousTs != null) {

            // compute duration
            int msDuration = (int) (currentTs.getTime() - previousTs.getTime());
            // get a record
            CamelDcr dcr = (CamelDcr) dcs.getDcr("CAMEL");
            // write record
            dcr.setDuration(msDuration);
            dcr.setExchangeId(exchangeId);
            dcr.setFileId(fileId);
            dcr.setFromEndpoint(fromEndpoint);
            dcr.setToNode(toNode);
            dcr.setTs(previousTs);
            dcs.save(dcr);
            // delete from hash
            msgCache.remove(key);
            // if we haven't, add to cache
        } else {
            msgCache.put(key, currentTs);
        }*/
    }
}
