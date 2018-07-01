package com.example.polls.config;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

//import io.prometheus.client.Counter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;


@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	
	
	
	@Autowired
    static  private MeterRegistry registry;
	
	//private Counter counter = Metrics.counter("handler.calls");
	
    /*static final Counter requests = Counter.build()
            .name("requests").help("Requests counter.").register();*/
  
    //private io.micrometer.core.instrument.Counter requests2 = Metrics.counter("requests2");

	/*
    static final Counter failed_requests = Counter.build() 
            .name("failed_requests").help("Failed requests")
            .labelNames("URI","METHOD","USER","response_status_code").register();
    */

     /*static final Counter requests_endpoints = Counter.build()
             .name("total_requests").help("Total Requests")
             .labelNames("URI","METHOD").register();*/
    

	
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {

    	System.out.println("In preHandle we are Intercepting the Request");
    	System.out.println("____________________________________________");
    	
    	
    	//counter.increment();
    	Metrics.counter("handler.calls").increment(); 
    	
    	Metrics.counter("handlerendpoint.calls", "URI",request.getRequestURI(),"METHOD",request.getMethod()).increment(); 
    	
        //requests2.increment();
        //requests_endpoints.labels(requestURI,request.getMethod()).inc();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView model)
            throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object object, Exception arg3)
            throws Exception {
       /* User user = UserLocalThread.get();*/
       if(!(response.getStatus()==200 || response.getStatus()==201 ||response.getStatus()==201)){
    	   Metrics.counter("handlerfail.calls", "URI",request.getRequestURI(),"METHOD",request.getMethod(), "status", String.valueOf(response.getStatus())).increment(); 
       	
        //    failed_requests.labels(request.getRequestURI(),request.getMethod(),/*user.getAccountName(),*/String.valueOf(response.getStatus())).inc();
       }
    }
}