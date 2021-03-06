package com.bupt;


import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import de.undercouch.bson4jackson.BsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;


import java.io.*;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张 成 on 2017/8/6.
 */
public class Test {



    private static MessageProtos.Message getProtobufBean() {
        com.bupt.MessageProtos.Message.Builder messageBuilder = MessageProtos.Message.newBuilder();

        messageBuilder.setStrObj("message");
        messageBuilder.setFolatObj(1f);
        messageBuilder.addDoubleObj(1d);
        messageBuilder.addDoubleObj(2d);
        messageBuilder.setBoolObj(true);

        messageBuilder.setBytesObj(ByteString.copyFrom(new byte[]{1, 2, 3}));
        messageBuilder.setInt32Obj(32);
        messageBuilder.setInt64Obj(64l);
        messageBuilder.setSint32Obj(232);
        messageBuilder.setSint64Obj(264);
        messageBuilder.setFixed32Obj(532);
        messageBuilder.setFixed64Obj(564);
        messageBuilder.setSfixed32Obj(2532);
        messageBuilder.setSfixed64Obj(2564);
        messageBuilder.setUint32Obj(632);
        messageBuilder.setUint64Obj(664);

        com.bupt.InnerMessageProtos.InnerMessage.Builder innerMessageBuilder = InnerMessageProtos.InnerMessage.newBuilder();
        innerMessageBuilder.setId(1);
        innerMessageBuilder.setName("inner");
        innerMessageBuilder.setType(EnumTypeProtos.EnumType.PRODUCTS);

        messageBuilder.setInnerMessage(innerMessageBuilder);

        return messageBuilder.build();
    }

    private static MessagePojo getPojoBean() {
        MessagePojo bean = new MessagePojo();

        bean.setStrObj("message");
        bean.setFolatObj(1f);
        List<Double> doubleObj = new ArrayList<Double>();
        doubleObj.add(1d);
        doubleObj.add(2d);
        bean.setDoubleObj(doubleObj);
        bean.setBoolObj(true);

        bean.setBytesObj(new byte[]{1, 2, 3});
        bean.setInt32Obj(32);
        bean.setInt64Obj(64l);
        bean.setSint32Obj(232);
        bean.setSint64Obj(264);
        bean.setFixed32Obj(532);
        bean.setFixed64Obj(564);
        bean.setSfixed32Obj(2532);
        bean.setSfixed64Obj(2564);
        bean.setUint32Obj(632);
        bean.setUint64Obj(664);

        InnerMessagePojo innerMessagePojo = new InnerMessagePojo();
        innerMessagePojo.setId(1);
        innerMessagePojo.setName("inner");
        innerMessagePojo.setType(EnumTypePojo.PRODUCTS);

        bean.setInnerMessage(innerMessagePojo);

        return bean;
    }

    private static void testTemplate(TestCallback callback, Object source, int count) {
        DecimalFormat integerFormat = (DecimalFormat) DecimalFormat.getIntegerInstance();
        int warmup = 10;
        // 先进行预热，加载一些类，避免影响测试
        for (int i = 0; i < warmup; i++) {
            byte[] bytes = callback.writeObject(source);
            callback.readObject(bytes);

        }
        restoreJvm(); // 进行GC回收
        // 进行测试
        long start = System.nanoTime();
        long size = 0l;
        for (int i = 0; i < count; i++) {
            byte[] bytes = callback.writeObject(source);
            size = size + bytes.length;
           // callback.readObject(bytes);
            // System.out.println(callback.readObject(bytes));
            bytes = null;
        }
        long nscost = (System.nanoTime() - start);
        System.out.println(callback.getName() + " total cost=" + integerFormat.format(nscost) + "ns , each cost="
                + integerFormat.format(nscost / count) + "ns , and byte sizes = " + size / count);
        restoreJvm();// 进行GC回收

    }

    private static void restoreJvm() {
        int maxRestoreJvmLoops = 10;
        long memUsedPrev = memoryUsed();
        for (int i = 0; i < maxRestoreJvmLoops; i++) {
            System.runFinalization();
            System.gc();

            long memUsedNow = memoryUsed();
            // break early if have no more finalization and get constant mem used
            if ((ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount() == 0)
                    && (memUsedNow >= memUsedPrev)) {
                break;
            } else {
                memUsedPrev = memUsedNow;
            }
        }
    }

    private static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }




    public static void main(String[] args) {
        final int testCount = 1000 * 500;
        final MessageProtos.Message protoObj = getProtobufBean();
        final MessagePojo pojoOBj = getPojoBean();





 //Serializable测试
        testTemplate(new TestCallback() {

            public String getName() {
                return "Serializable Test";
            }


            public byte[] writeObject(Object source) {
                try {
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    ObjectOutputStream output = new ObjectOutputStream(bout);
                    output.writeObject(source);
                    return bout.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }


            public Object readObject(byte[] bytes) {
                try {
                    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
                    ObjectInputStream input = new ObjectInputStream(bin);
                    return input.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, pojoOBj, testCount);

// protobuf测试
        testTemplate(new TestCallback() {

            public String getName() {
                return "protobuf test";
            }


            public byte[] writeObject(Object source) {
                if (source instanceof MessageProtos.Message) {
                    MessageProtos.Message message = (MessageProtos.Message) source;
                    return message.toByteArray();
                }

                return null;
            }


            public Object readObject(byte[] bytes) {
                try {
                    return MessageProtos.Message.parseFrom(bytes);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, protoObj, testCount);

// json测试
        final ObjectMapper objectMapper = new ObjectMapper();
        final JavaType javaType = TypeFactory.type(pojoOBj.getClass());

// JSON configuration not to serialize null field
        objectMapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

// JSON configuration not to throw exception on empty bean class
        objectMapper.getSerializationConfig().disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);

// JSON configuration for compatibility
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        testTemplate(new TestCallback() {

            public String getName() {
                return "Jackson Test";
            }


            public byte[] writeObject(Object source) {
                try {
                    return objectMapper.writeValueAsBytes(source);
                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }


            public Object readObject(byte[] bytes) {
                try {
                    return objectMapper.readValue(bytes, 0, bytes.length, javaType);
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, pojoOBj, testCount);

// //Xstream测试
        final XStream xstream = new XStream();
        testTemplate(new TestCallback() {

            public String getName() {
                return "Xstream test";
            }


            public byte[] writeObject(Object source) {
                return xstream.toXML(source).getBytes();
            }


            public Object readObject(byte[] bytes) {
                return xstream.fromXML(new ByteArrayInputStream(bytes));
            }
        }, pojoOBj, testCount);




        final BsonFactory factory = new BsonFactory();
        final com.fasterxml.jackson.databind.ObjectMapper mapper= new com.fasterxml.jackson.databind.ObjectMapper(factory);
        //final JavaType javaType = TypeFactory.type(pojoOBj.getClass());

        testTemplate(new TestCallback() {
            public String getName() {
                return "BSON test";
            }

            public byte[] writeObject(Object source) {
                try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                    mapper.writeValue(baos, source);
                   return baos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            public Object readObject(byte[] bytes) {
                ByteArrayInputStream bais = new ByteArrayInputStream(
                        bytes);
                try {
                    return mapper.readValue(bais, MessagePojo.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, pojoOBj, testCount);


    }

}
