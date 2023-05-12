package pl.luczak.michal.joboffersapp.ports.input;

/**
 * @param <RQ> request
 * @param <RP> response
**/
public interface UserController<RP, RQ> {

    RP findByUsername(String username);

    RP register(RQ rq);
}
