package com.wen.service;



import com.wen.pojo.Business;
import com.wen.pojo.Flower;
import com.wen.pojo.Flowercount;

import java.util.List;

public interface BusinessService {

    List<Flowercount> PendingOrders(String id);

    List<Flower> GoodsOnTheShelves(String user_id);

    List<Flowercount> OldOrders(String id);
}
