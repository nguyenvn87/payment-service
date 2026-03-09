package com.uit.utils;

public class PodcastView {
    // Chế độ xem cơ bản
    public interface Base {}

    // Chế độ xem chi tiết (mở rộng từ Summary)
    public interface Detail extends Base {}
}
