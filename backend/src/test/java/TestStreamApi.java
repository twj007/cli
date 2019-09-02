import com.common.utils.DateFormatUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/02
 **/
public class TestStreamApi {

    public static void main(String[] args) {
        List<StreamOperationDTO> list = new ArrayList<>();
        StreamOperationDTO o1 = new StreamOperationDTO();
        o1.setId(1L);
        o1.setPoint(1.0f);
        StreamOperationDTO o2 = new StreamOperationDTO();
        o2.setId(2L);
        o2.setPoint(2.0f);
        StreamOperationDTO o3 = new StreamOperationDTO();
        o3.setId(3L);
        o3.setPoint(3.0f);
        StreamOperationDTO o4 = new StreamOperationDTO();
        o4.setId(4L);
        o4.setPoint(4.0f);
        StreamOperationDTO o5 = new StreamOperationDTO();
        o5.setId(5L);
        o5.setPoint(5.0f);
        StreamOperationDTO o7 = new StreamOperationDTO();
        o7.setId(5L);
        o7.setPoint(5.0f);
        Function<Long, StreamOperationDTO> f6 = StreamOperationDTO::new;
        StreamOperationDTO o6 = f6.apply(6L);
        o6.setId(6L);
        o6.setPoint(6.0f);

        list.add(o1);
        list.add(o2);
        list.add(o3);
        list.add(o4);
        list.add(o5);
        list.add(o6);
        list.add(o7);
        List<StreamOperationDTO> l = list.stream()
                .filter(p -> p.getId() > 1L).collect(Collectors.toList());
        System.out.println("通过filter过滤id<=1的数据：" + l);
        List<Float> l2 = list.stream()
                .map(p -> p.getPoint()).collect(Collectors.toList());
        System.out.println("通过map获取指定的point：" + l2);
        List<StreamOperationDTO> l3 = list.stream()
                .sorted((p, f) ->  {return  Float.compare(p.getPoint(),  f.getPoint());})
                .collect(Collectors.toList());
        System.out.println("按份数排序：" + l3);
        Long l4 = list.stream()
                .filter(p -> p.getId() > 1L).count();
        System.out.println("count:" + l4);
        Float ff = list.stream()
                .filter(p -> p.getId() > 1L).map(StreamOperationDTO::getPoint).max(Comparator.comparing(Float::floatValue)).get();
        System.out.println(ff);

        Map<Long, List<StreamOperationDTO>> l7 = list.stream()
                .sorted((p, f) ->  {return  Float.compare(p.getPoint(),  f.getPoint());})
                .collect(Collectors.groupingBy(StreamOperationDTO::getId, Collectors.toList()));
        System.out.println(l7);
//        Map<Long, List<StreamOperationDTO>> l8 = list.stream()
//                .sorted((p, f) ->  {return  Float.compare(p.getPoint(),  f.getPoint());})
//                .collect(Collectors.groupingBy(StreamOperationDTO::getId, Collectors.reducing((f, p) -> ))));
    }
}
