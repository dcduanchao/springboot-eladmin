
package me.zhengjie.mongo.generator;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.mongo.primary.demo.domain.DemoTest;
import org.bson.types.ObjectId;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * mongoBean实体字段名称生成器
 * <p>
 * 字段名称对应mongodb文档字段名称 统一维护 避免出现修改字段名称 需要调整相应的repository层代码结构
 * <p>
 * 在generator中，加载entity，将entity中的所有field和innerBean中包含的field维护成一个tree{@link TreeNode}
 * <p>
 * 示例：
 * public static final String FIELD_ID = "id";
 * public static final String FIELD_ACTIONCODE = "actionCode";
 * public static final String FIELD_ACTIONNAME = "actionName";
 * public static final String FIELD_RELATIONID = "relationId";
 * public static final String FIELD_ACTIONDIFFICULTY = "actionDifficulty";
 * public static final String FIELD_ACTIONPOSITION = "actionPosition";
 * public static final String FIELD_TRAINPOSITIONS = "trainPositions";
 * public static final String FIELD_ACTIONCLASSIFY = "actionClassify";
 * public static final String FIELD_ACTIONUNIT = "actionUnit";
 * public static final String FIELD_TOOLWEIGHT = "toolWeight";
 * public static final String FIELD_HIGHFREQUENCY = "highFrequency";
 * public static final String FIELD_MIDDLEFREQUENCY = "middleFrequency";
 * public static final String FIELD_LOWFREQUENCY = "lowFrequency";
 * public static final String FIELD_RESTSECOND = "restSecond";
 * public static final String FIELD_EQUIPMENTS = "equipments";
 * public static final String FIELD_EXERCISEKIND = "exerciseKind";
 * public static final String FIELD_ACTIONESSENTIALS = "actionEssentials";
 * public static final String FIELD_CONSUMEPARAM = "consumeParam";
 * public static final String FIELD_CONSUMEPARAM_METS = "consumeParam.mets";
 * public static final String FIELD_CONSUMEPARAM_RER = "consumeParam.rer";
 * public static final String FIELD_CONSUMEPARAM_UPTAKEOXYMALE = "consumeParam.uptakeOxyMale";
 * public static final String FIELD_CONSUMEPARAM_UPTAKEOXYFEMALE = "consumeParam.uptakeOxyFemale";
 * public static final String FIELD_CONSUMEPARAM_OXYCONSUME = "consumeParam.oxyConsume";
 * public static final String FIELD_MATERIAL = "material";
 * </P>
 *
 *
 **/
@Slf4j
public class EntityFieldNameGenerator {

    /**
     * 需要处理的实体
     */
    private static Class DOMAIN_CLASS = null;

    /**
     * 字段模板
     */
    private static final String FIELD_TEMPLATE = "public static final String FIELD_%s = \"%s\";";

    /**
     * 字段值分隔符
     */
    private static final String FIELD_VALUE_SPLIT_CHAR = ".";

    /**
     * 字段名分隔符
     */
    private static final String FIELD_NAME_SPLIT_CHAR = "_";

    public static void main(String[] args) {
        generator(DemoTest.class);
    }


    /**
     * 生成方法
     * <p>
     * 生成静态的字段名称属性值
     * <p/>
     *
     * @param domain
     */
    public static void generator(Class domain) {
        if (domain == null) {
            log.error("实体类型不合法!!!");
            return;
        }
        DOMAIN_CLASS = domain;

        // 根节点
        TreeNode treeNode = new TreeNode(0, DOMAIN_CLASS.getSimpleName(), null);
        // 将所有的字段名称以树的形式存储
        addFieldNameToTree(DOMAIN_CLASS, treeNode);

        // 输出所有字段名称
        printFieldNameTree(treeNode);
        System.out.println("=================================================");

        // 将所有的字段名称拼接list
        List<String> fieldExpress = addFieldNameToList(treeNode, null);

        // 将追加的内容输出到控制台
        fieldExpress.forEach(System.out::println);
    }

    /**
     * 将字段名称添加到字段表达式列表中
     *
     * @param clazz
     * @param treeNode
     */
    private static void addFieldNameToTree(Class clazz, TreeNode treeNode) {
        Field[] declaredFields = clazz.getDeclaredFields();
        if (null == declaredFields || declaredFields.length <= 0) {
            log.error("{}类型字段信息空!!!", clazz.getSimpleName());
            return;
        }
        List<TreeNode> subTreeNodeList = treeNode.getSubTreeNodeList();

        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                String name = declaredField.getName();

                // 修饰符判断 公共、或者static直接pass
                int modifiers = declaredField.getModifiers();
                if (Modifier.isPublic(modifiers) || Modifier.isStatic(modifiers)) {
                    continue;
                }

                // 子节点
                TreeNode subNode = new TreeNode(treeNode.getSeq() + 1, name, treeNode);
                subTreeNodeList.add(subNode);
                // 进行类型推断
                Class<?> type = declaredField.getType();
                if (type.isPrimitive() || type.isEnum()) {
                    // 基本数据类型和枚举 直接处理
                    continue;
                } else if (isNoHandlerType(type.getName())) {
                    // 基本数据类型的报装类型和ObjectId、String 直接处理
                    continue;
                } else if (List.class.getName().equals(type.getName())) {
                    // list类型需要判断泛型的内容
                    ParameterizedTypeImpl genericType = (ParameterizedTypeImpl) declaredField.getGenericType();
                    Type actualTypeArgument = genericType.getActualTypeArguments()[0];
                    if (isNoHandlerType(actualTypeArgument.getTypeName())) {
                        continue;
                    }
                    type = Class.forName(actualTypeArgument.getTypeName());
                }
                addFieldNameToTree(type, subNode);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将所有的字段名输出
     *
     * @param root 根节点
     */
    private static void printFieldNameTree(TreeNode root) {
        if (null == root) {
            log.error("根节点为空...");
            return;
        }
        String fieldName = root.getFieldName();
        // 将treeNode节点打印出来
        for (int i = 0; i < root.getSeq(); i++) {
            System.out.print("  ");
        }
        System.out.println(fieldName);
        List<TreeNode> subTreeNodeList = root.getSubTreeNodeList();
        if (!CollectionUtils.isEmpty(subTreeNodeList)) {
            for (TreeNode treeNode : subTreeNodeList) {
                printFieldNameTree(treeNode);
            }
        }
    }

    /**
     * 将所有的字段名称进行整理放在list中
     *
     * @param treeNode
     * @param fieldExpressList
     */
    private static List<String> addFieldNameToList(TreeNode treeNode, List<String> fieldExpressList) {
        if (null == treeNode) {
            log.error("根节点为空...");
            return null;
        }
        if (null == fieldExpressList) {
            fieldExpressList = new ArrayList<>();
        }

        // 添加节点
        String fieldName = treeNode.getFieldName();
        if (!DOMAIN_CLASS.getSimpleName().equals(fieldName)) {
            String format = String.format(FIELD_TEMPLATE, renderingFieldName(treeNode), renderingFieldValue(treeNode));
            fieldExpressList.add(format);
        }

        // 子节点处理
        List<TreeNode> subTreeNodeList = treeNode.getSubTreeNodeList();
        if (!CollectionUtils.isEmpty(subTreeNodeList)) {
            for (TreeNode subNode : subTreeNodeList) {
                addFieldNameToList(subNode, fieldExpressList);
            }
        }
        return fieldExpressList;
    }

    /**
     * 渲染属性名称
     *
     * @param treeNode 节点信息
     * @return
     */
    private static String renderingFieldValue(TreeNode treeNode) {
        Stack<String> nodeNameList = getNodeNameList(treeNode, null);
        // 使用 "."分割
        List<String> result = stackToList(nodeNameList);
        if (CollectionUtils.isEmpty(result)) {
            return "";
        }
        return result.stream().collect(Collectors.joining(FIELD_VALUE_SPLIT_CHAR));
    }

    /**
     * 渲染属性值
     *
     * @param treeNode
     * @return
     */
    private static String renderingFieldName(TreeNode treeNode) {
        Stack<String> nodeNameList = getNodeNameList(treeNode, null);
        // 使用 "_"分割
        List<String> result = stackToList(nodeNameList);
        if (CollectionUtils.isEmpty(result)) {
            return "";
        }
        return result.stream().collect(Collectors.joining(FIELD_NAME_SPLIT_CHAR)).toUpperCase();
    }

    /**
     * 将树枝上的所有节点name放在list中
     *
     * @param treeNode
     * @param fieldNameStack
     */
    private static Stack<String> getNodeNameList(TreeNode treeNode, Stack<String> fieldNameStack) {
        if (null == treeNode) {
            return null;
        }
        if (null == fieldNameStack) {
            fieldNameStack = new Stack<>();
        }
        String fieldName = treeNode.getFieldName();
        if (!DOMAIN_CLASS.getSimpleName().equals(fieldName)) {
            fieldNameStack.push(fieldName);
        }

        // 父节点的处理
        TreeNode parant = treeNode.getParant();
        if (null != parant) {
            getNodeNameList(parant, fieldNameStack);
        }
        return fieldNameStack;
    }

    /**
     * 将stack的内容转换为list
     *
     * @param fieldNameStack
     * @return
     */
    private static List<String> stackToList(Stack<String> fieldNameStack) {
        if (CollectionUtils.isEmpty(fieldNameStack)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = fieldNameStack.size(); i > 0; i--) {
            list.add(fieldNameStack.pop());
        }
        return list;
    }

    /**
     * 判断是否是自定义的bean
     *
     * @param typeName
     * @return
     */
    private static boolean isNoHandlerType(String typeName) {
        if (StringUtils.isEmpty(typeName)) {
            return true;
        }
        try {
            Class<?> type = Class.forName(typeName);
            if (type.isPrimitive() || type.isEnum()) {
                return true;
            } else return String.class.getName().equals(typeName)
                    || ObjectId.class.getName().equals(typeName)
                    || Date.class.getName().equals(typeName)
                    || Integer.class.getName().equals(typeName)
                    || Boolean.class.getName().equals(typeName)
                    || BigDecimal.class.getName().equals(typeName)
                    || Float.class.getName().equals(typeName)
                    || Double.class.getName().equals(typeName)
                    || Long.class.getName().equals(typeName)
                    || Character.class.getName().equals(typeName)
                    || Timestamp.class.getName().equals(typeName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
