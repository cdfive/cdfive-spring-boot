package com.cdfive.springboot.webmvc.ext;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author cdfive
 */
public class ExtJsonMappingException extends JsonMappingException {

    private static final long serialVersionUID = -3179868099460816679L;

    public ExtJsonMappingException(String msg) {
        super(msg);
    }

    @Override
    protected String _buildMessage() {
        _path = null;
        return super._buildMessage();
    }
}
