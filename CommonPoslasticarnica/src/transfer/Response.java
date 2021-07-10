/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import java.io.Serializable;
import util.ResponseStatus;

/**
 *
 * @author Sony
 */
public class Response implements Serializable {

    private ResponseStatus status;
    private Object error;
    private Object data;

    public Response() {
    }

    public Response(ResponseStatus status, Object error, Object data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}