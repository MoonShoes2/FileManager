package filemanager.tool;

import org.springframework.stereotype.Component;

@Component
public class NumberTool {

    // 给两个数字计算百分比
    public static Double getPercent(Object part, Object total) {
        if (!(part instanceof Number) || !(total instanceof Number)) {
            throw new IllegalArgumentException("参数必须是数字类型");
        }
        double partValue = ((Number) part).doubleValue();
        double totalValue = ((Number) total).doubleValue();
        if (totalValue == 0) {
            throw new ArithmeticException("除数不能为 0");
        }
        double percent = partValue / totalValue * 100;
        return Double.valueOf(String.format("%.2f", percent)); // 不加 %
    }

}
