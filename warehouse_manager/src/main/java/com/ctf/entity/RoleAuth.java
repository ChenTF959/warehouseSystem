package com.ctf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ChenTF
 * @Date: 2023/9/17 20:02
 * @description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuth {

    private int roleAuthId;

    private int roleId;

    private int authId;
}
