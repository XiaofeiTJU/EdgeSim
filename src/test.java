class test {
	public static void main(String[] args) {
		// 先求Gbu 没有单位
		double dbu = 100;// 单位：米
		double Gbu = 140.7 + 36.7 * Math.log10(dbu * 0.001); // 损失+
		System.out.println("损失:" + Gbu);
		
		double Pb = 46.020599913279625; // 单位：W
		

		// 计算信噪比
		double temp = -174; // 单位dBm/HZ
		double o = (Math.pow(10, temp / 10)) * 0.001; // 单位W/HZ
		double SNR = Pb*Gbu / temp;
		System.out.println(SNR);
		
//		SNR = 30;
//		double sn = Math.pow(10, SNR/10);
//		System.out.println(sn);

		// 计算下载速度
		double B = 10000000; // 单位HZ
		double w = B * (Math.log(1 + SNR) / Math.log(2));

		System.out.println(w / 8 / 1024 / 1024 + " MB/s");
		
		System.out.println("______________");
//		new test().add();
	}

	public void add() {
		// 先求Gbu 没有单位
		double dbu = 350;// 单位：米
		double k = 0.01;
		double e = 4;
		double Gbu = k*Math.pow(dbu, -e);
		
		double Pb = 40; // 单位：W
		
		double temp = -174; // 单位dBm/HZ

		double o = (Math.pow(10, temp/10)) * 0.001; // 单位W/HZ

		double SNR = Pb*Gbu/(o*10000000);
		
//		System.out.println(SNR);
//
//		double sn = Math.pow(10, SNR/10);
//		System.out.println(sn);
		
//		sn = 30;
////		
//		SNR = Math.log10(30)*10;
//		System.out.println(SNR);
//		System.out.println(14.77*);
		
		double B = 10000000; // 单位HZ
		double w = B * (Math.log(1 + SNR) / Math.log(2));

		System.out.println(w / 8 / 1024 / 1024 + " MB/s");
	}
}