package com.hyw.platform.tools;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

public class CodeGenerator {

    public static void main(String[] args) {
        // 把表名写入list ，直接运行后就可以自动生成代码
        List<String> tables = new ArrayList<>(Arrays.asList("message_send_plan","message_send_data","sys_param"));

        for(String tableName : tables) {
            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();

            // 全局配置
            GlobalConfig gc = new GlobalConfig();
//            String projectPath = System.getProperty("user.dir");
            String projectPath = "E:\\wls\\aicode";
            gc.setOutputDir(projectPath + "/src/main/java");
            gc.setAuthor("jobob");
            gc.setOpen(true);
            mpg.setGlobalConfig(gc);

            // 数据源配置
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl("jdbc:mysql://10.20.16.15:5102/dfds?useUnicode=yes&characterEncoding=UTF8&useSSL=false");
            // dsc.setSchemaName("public");
            dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//            dsc.setUsername("deployop");
            dsc.setUsername("deployop");
            dsc.setPassword("iN9Wac@NAn*6");
            dsc.setTypeConvert(new MySqlTypeConvert() {
                // 自定义数据库表字段类型转换【可选】
                @Override
                public DbColumnType processTypeConvert(GlobalConfig gc,String fieldType) {
                    if ( fieldType.toLowerCase().contains( "int" ) ) {
                        return DbColumnType.LONG;
                    }
                    return (DbColumnType) super.processTypeConvert(gc, fieldType);
                }
            });
//            dsc.setPassword("T6cac85838223#");
            mpg.setDataSource(dsc);

            // 包配置
            PackageConfig pc = new PackageConfig();
//            pc.setModuleName(scanner("模块名"));
            pc.setParent("com.dashuf.dfds");
            pc.setEntity("model");
            pc.setController("web.controller");

            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("sterm", 0);
                    this.setMap(map);
                }
            };
            List<FileOutConfig> focList = new ArrayList<>();
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml" ;
                }
            });
            cfg.setFileOutConfigList(focList);
            mpg.setCfg(cfg);
            mpg.setTemplate(new TemplateConfig().setXml(null));

            // 策略配置
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//            strategy.setSuperEntityClass("com.dashuf.caes.model.BaseEntity");
            strategy.setSuperEntityColumns("created_by","created_date","updated_by","updated_date","version_val","record_ind");
            strategy.setRestControllerStyle(true);
//            strategy.setSuperControllerClass("com.dashuf.caes.controller.BaseController");
            strategy.setInclude(tableName);
            strategy.setEntityBuilderModel(true);
            strategy.setEntityLombokModel(true);
            strategy.setControllerMappingHyphenStyle(false);
            strategy.setTablePrefix(pc.getModuleName() + "_");
            mpg.setStrategy(strategy);
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
            mpg.execute();
        }

    }

}
