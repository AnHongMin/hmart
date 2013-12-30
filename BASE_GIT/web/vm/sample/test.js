
Ext.onReady(function() {
	Ext.QuickTips.init();

    // 검색
	var searchTab = Ext.widget({
		title: 'Search',
		xtype: 'form',
		id: 'searchForm',
		collapsible: true,
		bodyPadding: 5,
		items : [{
			xtype : 'container',
			layout : 'column',
			items : [{
				xtype : 'container',
				columnWidth : .3,
				items : [{
					xtype: 'textfield',
					fieldLabel: 'State',
					allowBlank: false,
					name: 'searchState',
					anchor:'95%'
				},{
					xtype: 'datefield',
					fieldLabel: 'Date From',
					name: 'searchDateFrom',
					anchor:'95%'
				}]
			},{
				xtype : 'container',
				columnWidth : .3,
				items : [{
					xtype: 'textfield',
					fieldLabel: 'Branch',
					allowBlank: false,
					name: 'searchBrach',
					anchor:'95%'
				},{
					xtype: 'datefield',
					fieldLabel: 'Date to',
					name: 'searchDateTo',
					anchor:'95%'
				}]
			},{
				layout : 'form',
				columnWidth : .3,
				items: [{
					xtype: 'button',
					text: 'Confirm',    	 
					width : 200,
					handler: function() {
						var form = this.up('form').getForm();
						if(form.isValid()){
							var values = form.getValues();
							gridStore.getProxy().extraParams.searchState = values['searchState'];
							gridStore.getProxy().extraParams.searchDateFrom = values['searchDateFrom'];
							gridStore.getProxy().extraParams.searchBrach = values['searchBrach'];
							gridStore.getProxy().extraParams.searchDateTo = values['searchDateTo'];
							gridStore.load();
						}
					}
				},{
					xtype: 'button',    	                	
					text: 'Cancel',
					width : 200,
					handler: function() {
						this.up('form').getForm().reset();
					}
				}]
			}]
		}]
	});
    searchTab.render(document.body);
    
    // 데이터셋
    var gridStore = new Ext.data.JsonStore({
    	storeId : 'gridStore',
    	pageSize : 99999,
    	remoteSort : false,
    	proxy : {
    		type : 'ajax',
    		url : '/sample.do?method=testData',
    		reader : {
    			type : 'json',
    			root : 'dataset',
    			idProperty : 'operator',
    			totalProperty : 'totalCount'
			},
			extraParams: {
				searchState : "",
				searchDateFrom : "",
				searchBrach : "",
				searchDateTo : ""	             
			}
    	},
    	fields : [ 'operator', { name : 'transaction', type : 'float' }, { name : 'ringtime', type : 'float' }, 'net', 'item'],
    	autoLoad : false
	});

    // 데이터셋 출력(그리드)
    // ext4-chart-sales-portion-by-category.jsp > sales_itemGrid
	Ext.create('Ext.grid.Panel', {
		id : 'test-grid',
		title : 'Data List',
		store : Ext.data.StoreManager.lookup('gridStore'),
		collapsible:false,
		columns : [{
					text : 'OPERATOR',
					dataIndex : 'operator'
				},{
					text : 'TRANSTION',
					dataIndex : 'transaction'
				},{ 
					text : 'RINGTIME',
					dataIndex : 'ringtime',
					renderer : function(value) {
						var tmpl = "<div style=\"color:red\">{0}</div>";
						return Ext.String.format(tmpl, value);
					}
				},{
					text : 'NET', 		
					dataIndex : 'net'
				},{
					text : 'ITEM', 		
					dataIndex : 'item'
				}],
		listeners : {
			celldblclick : function(obj, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var store = this.getStore().getAt(rowIndex).data;
				var count = 0;
				var clickRowDataValue = "";
				for ( var j in store) {
					//console.log(count + " : " + store[j]);
					if (count == (cellIndex + 1)) {
						clickRowDataValue = store[j];
					}
					count++;
				}
				Ext.Msg.alert('MSG', 'operator : ' + record.get('operator') + ", " + clickRowDataValue);
			}
		},
	/*
		,bbar : Ext.create('Ext.PagingToolbar', {
	    	id : 'test-grid-Pagingbar',
	    	store : gridStore,
	    	displayInfo : true,
	    	displayMsg : 'Displaying topics {0} - {1} of {2}',
	    	emptyMsg : "No topics to display"            	
	    })
*/	   
		height : 400,
		renderTo : Ext.getBody(),
		buttons : [{
			// grid row 추가 샘플
			text : 'ADD',
			handler : function() {
				var newData = { 'transaction' : 1836 };
				gridStore.addSorted(newData);
				//store.load();
			}
		},{
			// grid row 삭제 샘플
			text : 'DEL',
			handler : function() {
				var row_selected = Ext.getCmp('test-grid').getSelectionModel().getSelection();
				Ext.getCmp('test-grid').getStore().remove(row_selected);
			}
		}]
	});
});
