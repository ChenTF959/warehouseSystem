package com.ctf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ChenTF
 * @Date: 2023/9/15 9:57
 * @description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    private int userRoleId;

    private int roleId;

    private int userId;
}
