package com.jhmarryme.demo.pojo.vo;

import com.jhmarryme.demo.common.base.interfaces.BasisGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;


/**
 *
 * @author JiaHao Wang
 * @date 2021/3/5 11:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestVO {

    @NotEmpty(message = "{demo.name.null}")
    @NotNull(groups = {BasisGroups.UpdateGroup.class, Default.class})
    private String name;

    @Length(min = 5, max = 25, message = "{demo.key.length}", groups = {BasisGroups.UpdateGroup.class, Default.class})
    private String key;

    @Pattern(regexp = "[012]", message = "无效的状态标志", groups = {BasisGroups.InsertGroup.class, Default.class})
    private String state;
}
