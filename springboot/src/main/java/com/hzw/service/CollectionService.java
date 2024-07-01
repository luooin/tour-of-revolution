package com.hzw.service;

import com.hzw.comon.QueryPageParam;
import com.hzw.comon.ResponseResult;
import com.hzw.entity.Collection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzw.qo.CollectQuery;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzw
 * @since 2023-01-26
 */
public interface CollectionService extends IService<Collection> {

    ResponseResult saveCollection(Collection collection);

    ResponseResult findCollectionByPage(CollectQuery query);

    ResponseResult isCollection(Long productId,Long productType);
}
