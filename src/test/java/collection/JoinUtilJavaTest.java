package collection;

import io.github.alexbogovich.collection.JoinUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

public class JoinUtilJavaTest {

    @Test
    public void test() {
        List<List<Integer>> lists = asList(asList(1, 2, 5), asList(3, 4, 10), asList(88, 99, 44));
        Collection<Collection<Integer>> join = JoinUtil.crossJoin(lists);
        System.out.println(join);
        Assert.assertEquals(27, join.size());
        join.forEach(l -> Assert.assertEquals(3, l.size()));
    }
}
