package pl.luczak.michal.joboffersapp.ports.input.user;

/**
 * @param <RQ> request
 * @param <RP> response
**/
public interface UserControllerPort<RP, RQ> {

    RP findByUsername(String username);

    RP register(RQ rq);
}
