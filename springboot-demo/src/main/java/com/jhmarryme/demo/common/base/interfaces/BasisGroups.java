package com.jhmarryme.demo.common.base.interfaces;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * 默认的Validation校验规则组
 * @author JiaHao Wang
 * @date 2021/3/5 17:06
 */
public interface BasisGroups {

    interface InsertGroup {};
    interface DeleteGroup {};
    interface UpdateGroup {};
    interface QueryGroup {};

    /**
     * 根据目前的先后顺序进行验证，当存在一个验证不通过的情况则不会验证后一个分组, 最后是Default
     *      关于Default，此处我springValidation默认生成的验证接口，验证的范围是所有带有验证信息的属性，
     *      若是属性上方写了验证组，则是验证该组内的属性
     *      若是验证实体类类上写了GroupSequence({}) 则说明重写了Default验证接口，Default就按照GroupSequence里所写的组信息进行验证
     */
    @GroupSequence({InsertGroup.class, DeleteGroup.class, UpdateGroup.class, QueryGroup.class})
    interface DefaultSequence{}
}
