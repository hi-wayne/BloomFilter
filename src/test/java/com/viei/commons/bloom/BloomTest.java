package com.viei.commons.bloom;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class BloomTest {
	public static void main(String[] arg) {
		//如果实际添加元素大于预定义元素个数，误判精度会提高
		double falsePositiveProbability = 0.01;// 误判精度
		int expectedNumberOfElements = 1000;// 元素个数

		// 初始化bloom
		BloomFilter<String> bloomFilter = new BloomFilter<String>(
				falsePositiveProbability, expectedNumberOfElements);
		System.out.println(bloomFilter.expectedFalsePositiveProbability());
		try {

			MessageDigest md5 = MessageDigest.getInstance("MD5");

			for (int i = 0; i < expectedNumberOfElements; i++) {
				String info = "foo" + i;
				String md5str = new String(DigestUtils.md5Hex(info));
				// 添加元素
				bloomFilter.add(md5str);
			}
			System.out.println("input ok");
			for (int i = 0; i < expectedNumberOfElements; i++) {
				String info = "foo" + i;
				String md5str = new String(DigestUtils.md5Hex(info));
				// 比较是否存在
				if (!bloomFilter.contains(md5str))
					System.out.println(info);
			}
			System.out.println("check ok");

			String info = "foo";
			String md5str = new String(DigestUtils.md5Hex(info));
			System.out.println(bloomFilter.contains(md5str));

			info = "foo999";
			md5str = new String(DigestUtils.md5Hex(info));
			System.out.println(info + "," + md5str + ","
					+ bloomFilter.contains(md5str));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
