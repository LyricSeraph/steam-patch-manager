package top.lyriclaw.spm.backend.vo.response;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
@Builder
@Slf4j
public class MyPage<T> {

    private final List<T> data;
    private final long total;
    private final int page;
    private final int size;

    public MyPage(List<T> data, long total, int page, int size) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public <R> MyPage<R> convert(Function<T, R> converter) {
        var result = new MyPage<>(new ArrayList<R>(), getTotal(), getPage(), getSize());
        for (T t : getData()) {
            result.data.add(converter.apply(t));
        }
        return result;
    }


}
