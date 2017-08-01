package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.mapper.indentAllocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/31.
 */
@Service
public class indentAllocationService {

    @Autowired
    private indentAllocationMapper indentAllocatMapper;

    public List<IndentAllocationEntity> findOrder(){

        return indentAllocatMapper.findorderId();
    }

    public List<IndentAllocationEntity> findtechnicianId(){

        return indentAllocatMapper.findorderId();
    }

    public IndentAllocationEntity find(String indentId){
       return indentAllocatMapper.find(indentId);
    }
    public List<IndentAllocationEntity> findlist(String technician){
        return indentAllocatMapper.findlist(technician);
    }
}
