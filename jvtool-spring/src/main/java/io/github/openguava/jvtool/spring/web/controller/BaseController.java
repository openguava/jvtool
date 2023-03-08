package io.github.openguava.jvtool.spring.web.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * BaseController
 * @author openguava
 *
 */
public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	this.onInitBinder(binder);
    }
    
    protected void onInitBinder(WebDataBinder binder) {
    	
    }
}
