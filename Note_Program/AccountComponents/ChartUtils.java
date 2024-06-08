package AccountComponents;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ChartUtils {

    public static JPanel createPieChart(Map<String, Double> data, String title) {
        return new PieChartPanel(data, title);
    }

    public static JPanel createBarChart(Map<String, Map<String, Double>> data, String title) {
        return new BarChartPanel(data, title);
    }

    static class PieChartPanel extends JPanel {
        private Map<String, Double> data;
        private String title;
        private List<Color> colors;

        public PieChartPanel(Map<String, Double> data, String title) {
            this.data = data;
            this.title = title;
            this.setPreferredSize(new Dimension(600, 400));
            this.colors = List.of(
                Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA,
                Color.CYAN, Color.YELLOW, Color.PINK, Color.LIGHT_GRAY, Color.GRAY
            );
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int minDim = Math.min(width, height);
            int diameter = minDim - 100;
            int x = (width - diameter) / 2;
            int y = (height - diameter) / 2;

            double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
            double curValue = 0.0;
            int startAngle;

            int colorIndex = 0;
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                startAngle = (int) (curValue * 360 / total);
                int arcAngle = (int) (entry.getValue() * 360 / total);

                g2.setColor(colors.get(colorIndex % colors.size()));
                g2.fillArc(x, y, diameter, diameter, startAngle, arcAngle);
                curValue += entry.getValue();
                colorIndex++;

                // 中心點座標
                double angle = Math.toRadians(startAngle + arcAngle / 2);
                int centerX = x + diameter / 2;
                int centerY = y + diameter / 2;

                // 計算文字位置
                int labelX = (int) (centerX + diameter * 0.35 * Math.cos(angle));
                int labelY = (int) (centerY + diameter * 0.35 * Math.sin(angle));

                // 繪製類別標籤
                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                String label = entry.getKey();
                int labelWidth = fm.stringWidth(label);
                int labelHeight = fm.getAscent();
                g2.drawString(label, labelX - labelWidth / 2, labelY + labelHeight / 2);
            }

            g2.setColor(Color.BLACK);
            g2.drawString(title, 20, 20);
        }
    }

    static class BarChartPanel extends JPanel {
        private Map<String, Map<String, Double>> data;
        private String title;

        public BarChartPanel(Map<String, Map<String, Double>> data, String title) {
            this.data = data;
            this.title = title;
            this.setPreferredSize(new Dimension(600, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);
            g2.setColor(Color.BLACK);
            g2.drawString(title, 20, 20);

            int barWidth = width / (data.size() * 4); // 更新這裡，使條形圖顯示更加合理
            int maxBarHeight = height - 100;

            int x = 50;
            for (Map.Entry<String, Map<String, Double>> entry : data.entrySet()) {
                String category = entry.getKey();
                Map<String, Double> values = entry.getValue();

                int y = height - 50;
                for (Map.Entry<String, Double> valueEntry : values.entrySet()) {
                    String month = valueEntry.getKey();
                    double value = valueEntry.getValue();

                    int barHeight = (int) (value / getMaxValue() * maxBarHeight);

                    g2.setColor(getRandomColor());
                    g2.fillRect(x, y - barHeight, barWidth, barHeight);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x, y - barHeight, barWidth, barHeight);

                    g2.drawString(month, x, y + 20);
                    g2.drawString(category, x, y + 35); // 在條形圖下方繪制類別名稱

                    x += barWidth + 10;
                }

                x += barWidth;
            }
        }

        private double getMaxValue() {
            return data.values().stream()
                    .flatMap(m -> m.values().stream())
                    .mapToDouble(Double::doubleValue)
                    .max().orElse(1);
        }

        private Color getRandomColor() {
            return new Color((int) (Math.random() * 0x1000000));
        }
    }

}
