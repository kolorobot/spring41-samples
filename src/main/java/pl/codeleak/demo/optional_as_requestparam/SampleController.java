package pl.codeleak.demo.optional_as_requestparam;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("o")
public class SampleController {

    @RequestMapping(value = "r", produces = "text/plain")
    public String requestParamAsOptional(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "ld") Optional<LocalDate> localDate) {

        StringBuilder result = new StringBuilder("ld: ");
        localDate.ifPresent(value -> result.append(value.toString()));

        return result.toString();
    }

    @RequestMapping(value = "h", produces = "text/plain")
    public String requestHeaderAsOptional(
            @RequestHeader(value = "Custom-Header") Optional<String> header) {

        StringBuilder result = new StringBuilder("Custom-Header: ");
        header.ifPresent(value -> result.append(value));

        return result.toString();
    }

    @RequestMapping(value = "m/{id}", produces = "text/plain")
    public String execute(@PathVariable Integer id,
                          @MatrixVariable Optional<Integer> p,
                          @MatrixVariable Optional<Integer> q) {

        StringBuilder result = new StringBuilder();
        result.append("p: ");
        p.ifPresent(value -> result.append(value));
        result.append(", q: ");
        q.ifPresent(value -> result.append(value));

        return result.toString();
    }
}
