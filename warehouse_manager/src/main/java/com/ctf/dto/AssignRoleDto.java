package com.ctf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ChenTF
 * @Date: 2023/9/16 16:57
 * @description:接收分配角色请求json数据的Dto类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleDto {

    private Integer userId;

    private List<String> roleCheckList;
}
