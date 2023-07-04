package pl.luczak.michal.joboffersapp.ports.input.user;

/**
 * @param <RQ> request
 * @param <RP> response
**/
public interface UserControllerPort<RP, RQ> {

    RP register(RQ rq);
}
