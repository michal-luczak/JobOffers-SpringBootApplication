package pl.luczak.michal.joboffersapp.ports.input.user;

/**
 * @param <Request> request
 * @param <Response> response
**/
public interface UserControllerPort<Response, Request> {

    Response register(Request request);
}
