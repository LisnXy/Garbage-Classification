import os


# 检测名称，观察是否可以直接进行分类
def name_check(file_name):
    if file_name.find("可回收") != -1:
        return 1
    if file_name.find("有害垃圾") != -1:
        return 2
    if file_name.find("厨余") != -1:
        return 3
    if file_name.find("其他") != -1:
        return 4
    return 0


class ConvClassifier:
    def __init__(self):
        self.recycle = ['传单', '充电宝', '包', '塑料玩具', '塑料碗盆', '塑料衣架', '快递纸袋', '报纸', '插头电线', '旧书', '旧衣服', '易拉罐', '杂志', '枕头',
                        '毛绒玩具', '泡沫塑料', '洗发水瓶', '牛奶盒等利乐包装', '玻璃', '玻璃瓶罐', '皮鞋', '砧板', '纸板箱', '调料瓶', '酒瓶', '金属食品罐', '锅',
                        '食用油桶', '饮料瓶']
        self.kitchen = ['剩菜剩饭', '大骨头', '果壳瓜皮', '残枝落叶', '水果果皮', '水果果肉', '茶叶渣', '菜梗菜叶', '落叶', '蛋壳', '西餐糕点', '鱼骨']
        self.harmful = ['干电池', '废弃水银温度计', '废旧灯管灯泡', '杀虫剂容器', '电池', '软膏', '过期药物', '除草剂容器']
        self.etc = ['一次性餐具', '化妆品瓶', '卫生纸', '尿片', '污损塑料', '烟蒂', '牙签', '破碎花盆及碟碗', '竹筷', '纸杯', '贝壳']

    def classify(self, class_name):
        # 如果名称带有分类信息，则直接分类
        flag = name_check(class_name)
        if flag != 0:
            return flag
        # 否则在现有分类信息中查找
        for item in self.recycle:
            if item == class_name:
                return 1

        for item in self.harmful:
            if item == class_name:
                return 2

        for item in self.kitchen:
            if item == class_name:
                return 3
        # 如果现有分类信息当中没有，则返回为其他垃圾
        return 4

    def name_process(self, file_name):
        if file_name.find('_') == -1:
            return file_name
        res_set = file_name.split('_')
        if file_name.find("_") == 4:
            res = res_set[1]
        else:
            res = res_set[0]
        return res
