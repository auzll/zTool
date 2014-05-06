<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoFullName}">
    <resultMap id="${entityName}Mapper" type="${entityPackageName}.${entityName}">
        <!--resultMap-->
    </resultMap>
  
    <insert id="save" useGeneratedKeys="true" keyProperty="id">
        <!--save-->
    </insert>
    
    <delete id="delete">
        delete from ${tableName} where id = #{id}
    </delete>
  
    <update id="update">
        <!--update-->
    </update>
  
    <select id="queryById" resultMap="${entityName}Mapper">
        select * from ${tableName} where id = #{entity.id} and creator = #{entity.creator.id}
    </select>

    <select id="queryListByIdList" resultMap="${entityName}Mapper">
        select * from ${tableName} where id in 
            <foreach collection="list" open="(" close=")" separator="," item="id">#{id}</foreach> 
    </select>
    
    <select id="queryPageList" resultMap="${entityName}Mapper">
        select * 
          from ${tableName}
        <trim prefix="where" prefixOverrides="and">
            <if test="null != entity">
                <if test="null != entity.creator">
                    and creator = #{entity.creator.id}
                </if>
                <if test="entity.id &gt; 0">
                    and id = #{entity.id}
                </if>
            </if>
         </trim>
         order by id desc
         limit #{start}, #{limit}
    </select>
    
    <select id="queryPageCount" resultType="int">
        select count(*) 
          from ${tableName}
        <trim prefix="where" prefixOverrides="and">
            <if test="null != entity">
                <if test="null != entity.creator">
                    and creator = #{entity.creator.id}
                </if>
                <if test="entity.id &gt; 0">
                    and id = #{entity.id}
                </if>
            </if>
         </trim>
    </select>
    
</mapper>
