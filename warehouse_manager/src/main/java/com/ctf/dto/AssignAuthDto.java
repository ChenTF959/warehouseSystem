package com.ctf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/18 15:07
 * @description:接收给角色分配权限请求的json数据的dto类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignAuthDto {

    //接收角色id
    private Integer roleId;

    //接收角色分配的所有菜单权限的id
    private List<Integer> authIds;
}
