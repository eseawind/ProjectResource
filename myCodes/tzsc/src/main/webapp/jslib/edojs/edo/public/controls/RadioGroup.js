/*RadioGroup.set({
	id: 'abc',
	displayField: 'label',
	checkField: 'checked',
	valueField: 'value',

	repeatDirection: 'vertical'/ 'horizontal',
	repeatItems: 10,
	itemWidth: 100,
	itemHeight: 22,

	data: [
		{label: '1', checked: false, value: 1},
		{label: '2', checked: false, value: 2},
		{label: '3', checked: false, value: 3}
	]
});
1.数据结构, 是一个数组,dataTable
2.显示形式:

abc.set('value', [1,2]);
abc.select(2);
abc.deselect(1);
*/
/**
    @name Edo.controls.RadioGroup
    @class
    @typeName RadioGroup
    @description 多选框列表
    @extend Edo.controls.Control
*/
Edo.controls.RadioGroup = function(){

    Edo.controls.RadioGroup.superclass.constructor.call(this);
};
Edo.controls.RadioGroup.extend(Edo.controls.CheckGroup,{
    /**
        @name Edo.controls.RadioGroup#multiSelect
        @property
        @default false
    */
    multiSelect: false,
    mustSelect: true,
    elCls: 'e-radiogroup',
    itemCls: 'e-checkgroup-item'
});

Edo.controls.RadioGroup.regType('radiogroup');   