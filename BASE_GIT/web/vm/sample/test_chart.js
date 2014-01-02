
Ext.onReady(function() {
	Ext.QuickTips.init();

    var hr_states_store = new Ext.data.Store({
		id : 'hr_states_store',
		proxy : {    			
			type : 'ajax',
			reader : {
				type : 'json',
				root : 'data'
			},
			url : '/vm/sample/stateData.vm',
			method : 'GET'
		}, 			
		fields : [ { name : 'stateid', mapping : 'stateid' }, 
		           { name : 'statename', mapping : 'statename'}
		],
		autoLoad : true,
		listeners : {
 			load : function() {
 				Ext.getCmp('hr-search-form-state-combo').select('1');
 			}
 		}
	}); 

    var hr_branch_store = new Ext.data.Store({
		id : 'hr_branch_store',
		proxy : {
			type : 'ajax',
			reader : {
				type : 'json',
				root : 'data',
				totalProperty : 'totalCount'
			},
			extraParams : {     		
				stateid : 1
			},
			url : '/sample.do?method=branchData',
			method : 'GET'
		},
		fields : [ { name : 'branchid', mapping : 'branchid' },
		           { name : 'branchname', mapping : 'branchname'}
		],
		autoLoad : true,
		listeners : {
 			load : function() {
 	 			Ext.getCmp('hr-search-form-branch-combo').select(this.getAt(0).get('branchid'));
 			}
 		}
	});
    
    // 차트 데이터
    var hr_chartStore = new Ext.data.JsonStore({
    	storeId : 'hr_chartStore',
    	proxy : {
    		type : 'ajax',
    		url : '/sample.do?method=testData',
    		reader : {
    			type : 'json',
    			root : 'dataset',
    			idProperty : 'operator'
			},
			// 확장 파라미터 설정
			extraParams: {
				searchState : "1",
				searchDateFrom : "",
				searchBrach : "1",
				searchDateTo : "",
				searchDateType : ""	             
			}
    	},
		fields : [ 'operator', 'transaction', 'ringtime', 'net', 'item'],
		autoLoad : true
	});

    // 검색
    var hr_searchTab = Ext.widget({
        title: 'Search',
        xtype: 'form',
        id: 'hr-search-form',
        name : 'hr-search-form-name',
        collapsible: true,
        bodyPadding: 5,
        items : [{
        	xtype : 'container',
        	layout : 'column',
        	items : [{
        		xtype : 'container',
        		columnWidth : .3,
        		items : [{
        			id : 'hr-search-form-state-combo',
        			xtype: 'combobox',		
        			fieldLabel: 'State',
        			name: 'searchState',
        			anchor:'95%',
        			store:hr_states_store,
        			displayField:'statename',
        			valueField:'stateid',
					editable : false,
					allowBlank : false,
        			listeners: {
        				select: function() {
        					hr_branch_store.getProxy().extraParams.stateid = this.getValue();
        					hr_branch_store.load();
        				}
        			}
        		},{
        			xtype: 'datefield',
        			fieldLabel: 'Date From',
        			name: 'searchDateFrom',
        			format : 'Y-m-d',
        			anchor:'95%',
					editable : false,
					allowBlank : false
        		}]
        	},{
        		xtype : 'container',
        		columnWidth : .3,
        		items : [{
        			id : 'hr-search-form-branch-combo',
        			xtype: 'combobox',
        			fieldLabel: 'Branch',
        			name: 'searchBrach',
        			anchor:'95%',
        			store:hr_branch_store,
        			displayField:'branchname',
        			valueField:'branchid',
					editable : false,
					allowBlank : false
        		},{
        			xtype: 'datefield',
        			fieldLabel: 'Date To',
        			name: 'searchDateTo',
        			format : 'Y-m-d',
        			anchor:'95%',
					editable : false,
					allowBlank : false
        		}]
        	},{
        		layout : 'form',
        		columnWidth : .3,
        		items: [{
        			xtype: 'button',
        			text: 'SEARCH',
        			width : 200,
        			handler: function() {
        				var form = this.up('form').getForm();
        				if(form.isValid()){
        					var values = form.getValues();
        					hr_chartStore.getProxy().extraParams.searchState = values['searchState'];
        					hr_chartStore.getProxy().extraParams.searchDateFrom = values['searchDateFrom'];
        					hr_chartStore.getProxy().extraParams.searchBrach = values['searchBrach'];
        					hr_chartStore.getProxy().extraParams.searchDateTo = values['searchDateTo'];
        					hr_chartStore.getProxy().extraParams.searchDateType = values['searchDateType'];
        					hr_chartStore.load();
        				}
        			}
        		},{
        			xtype: 'button',
        			text: 'RESET',
        			width : 200,
        			handler: function() {
        				this.up('form').getForm().reset();
        				hr_branch_store.removeAll();
        			}
        		}]
        	},{
        		columnWidth : .6,
				xtype: 'checkboxgroup',
				columns: 8,
				allowBlank : false,
				items: [
					{ boxLabel: 'All', name: 'searchDateType', inputValue: '0' },
					{ boxLabel: 'MON', name: 'searchDateType', inputValue: '1' },
					{ boxLabel: 'TUE', name: 'searchDateType', inputValue: '2' },
					{ boxLabel: 'WED', name: 'searchDateType', inputValue: '3' },
					{ boxLabel: 'THU', name: 'searchDateType', inputValue: '4' },
					{ boxLabel: 'FRI', name: 'searchDateType', inputValue: '5' },
					{ boxLabel: 'SAT', name: 'searchDateType', inputValue: '6' },
					{ boxLabel: 'SUN', name: 'searchDateType', inputValue: '7' }
				]
        	}]
        }]
    });
    
    // 차트 출력(그리드)
    var hr_chart = Ext.create('Ext.chart.Chart', {
    	animate: true,
    	style: 'background:#fff',
    	shadow: false,
    	store: hr_chartStore,
    	width: 800,
    	height: 300,
    	axes: [{
    		type: 'Category',
    		position: 'bottom',
    		fields: ['operator'],
    		title: 'OPERATOR'
    	}, {
    		type: 'Numeric',
    		position: 'left',
    		fields: ['transaction'],
    		label: {
    			renderer: Ext.util.Format.numberRenderer('0,0')
    		},
    		title: 'Rate(%)',
    		minimum: 0
    	}],
    	series: [{
            type: 'column',
            axis: 'left',
            label: {
            	display: 'insideEnd',
                field: 'transaction',
                renderer: Ext.util.Format.numberRenderer('0'),
                orientation: 'horizontal',
                color: '#333',
                'text-anchor': 'middle'
            },
            xField: 'operator',
            yField: ['transaction'],
            renderer: function(sprite, record, attr, index, store) {
            	//var fieldValue = Math.random() * 20 + 10;
                var value = (record.get('transaction') >> 0) % 5;
                var color = ['rgb(213, 70, 121)', 
                             'rgb(44, 153, 201)', 
                             'rgb(146, 6, 157)', 
                             'rgb(49, 149, 0)', 
                             'rgb(249, 153, 0)'][value];
                return Ext.apply(attr, {
                    fill: color
                });
            },
            listeners : {
            	itemmouseup : function(obj) {
            		Ext.getCmp('searchCashier').setValue(obj.storeItem.data['operator']);
            	}
            }
    	}]
    });
    
    var hr_resultTab = Ext.widget({
    	xtype: 'form',
    	id: 'resultForm',
    	bodyPadding: 5,
    	items : [{
    		xtype : 'form',
    		layout : 'column',
    		items : [{
    			xtype : 'displayfield',
    			columnWidth : .1           			   
    		},{
    			xtype : 'container',
    			columnWidth : .9,
    			layout : 'form',
    			items : [{
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items: [{
    					xtype : 'displayfield',
    					value : 'Best Performance Cashier ',
    					width : 200
    				},{
    					xtype : 'textfield',
    					name : 'searchCashier',
    					width : 50,
    					allowBlank : false,
    					id : 'searchCashier',
    					readOnly :true,
    					fieldStyle: 'background-color: #ddd; background-image: none;'
    				},{
    					xtype : 'displayfield',
    					width : 10
    				},{
    					xtype : 'button',
    					text : 'Detail',
    					width : 50,
    					handler: function() {
    						var form = this.up('form').getForm();
    						var values = form.getValues();
    						if (typeof console == "object") {
    							console.log(values);
    						}
    					}
    				}]
    			}, {
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items: [{
    					xtype : 'displayfield',
    					value : 'Standard Rate',
    					width : 200
    				}, {
    					xtype : 'numberfield',
    					name: 'searchRate',
    					id : 'hr-search-rate',
    					width : 50,
    					allowBlank : false,
    					value : '85'
    				}, {
    					xtype : 'displayfield',
    					width : 10							    
    				}, {
    					xtype : 'displayfield',
    					value : '% Expected Sales Amount $',	
    					width : 175
    				}, {
    					xtype : 'textfield',		
    					name: 'searchAmount',
    					width : 55,
    					value : "10000" 
    				},{
    					xtype : 'displayfield',
    					width : 10
    				},{
    					xtype : 'button',
    					text : 'Confirm',
    					width : 70,
    					handler: function() {
    						for(var i=1; i<6; i++){
    							Ext.getCmp('result_data_'+i).setValue(this.up('form').getForm().getValues()['searchAmount']*i);	
    						}
    					}
    				}]
    			}, {
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items : [{
    					xtype : 'displayfield',
    					value : 'Needed',
    					width : 55								
    				}, {
    					xtype : 'textfield',
    					id : 'result_data_1',
    					width : 50,
    					readOnly : true,
    					fieldStyle: 'background-color: #ddd; background-image: none;'
    				}, {
    					xtype : 'displayfield',
    					width : 10							    
    				}, {
    					xtype : 'displayfield',
    					value: 'Standard cashiers on selected date'							    
    				}]
    			}, {
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items : [{
    					xtype : 'displayfield',
    					value : 'Total',
    					width : 55								
    				}, {
    					xtype : 'textfield',
    					id : 'result_data_2',
    					width : 50,
    					readOnly : true,
    					fieldStyle: 'background-color: #ddd; background-image: none;' 
    				}, {      
    					xtype : 'displayfield',
    					width : 10							    
    				}, {      
    					xtype : 'displayfield',
    					value : 'man-hour expected'							    
    				}]
    			}, {
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items : [{
    					xtype : 'displayfield',
    					value : 'They Can cover',
    					width : 100								
    				}, {
    					xtype : 'textfield',
    					id : 'result_data_3',
    					width : 50,
    					readOnly : true,
    					fieldStyle: 'background-color: #ddd; background-image: none;'
    				}, {      
    					xtype : 'displayfield',
    					width : 10							    
    				}, {      
    					xtype : 'displayfield',
    					value : 'transactions'							    
    				}]
    			}, {
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items : [{
    					xtype : 'displayfield',		
    					value : 'They Can cover',				
    					width : 100
    				}, {
    					xtype : 'textfield',
    					id : 'result_data_4',
    					width : 50,
    					readOnly : true,
    					fieldStyle: 'background-color: #ddd; background-image: none;'
    				}, {      
    					xtype : 'displayfield',		
    					width : 10
    				}, {
    					xtype : 'displayfield',
    					value : 'items'
    				}]
    			}, {
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items : [{
    					xtype : 'displayfield',		
    					value : 'They Can cover',				
    					width : 100
    				}, {
    					xtype : 'textfield',
    					id : 'result_data_5',
    					width : 50,
    					readOnly : true,
    					fieldStyle: 'background-color: #ddd; background-image: none;'
    				}, {
    					xtype : 'displayfield',		
    					width : 10							    
    				}, {
    					xtype : 'displayfield',		
    					value : 'amount sales'							    
    				}]
    			}]
    		}]
    	}]
    });
            	  
    var hr_chartTab = {
       	xtype: 'container',
       	layout : 'column',
       	items : [{
			xtype : 'displayfield',
			columnWidth : .05           			   
		},{
			xtype : 'container',
			columnWidth : .9,
			items : [ hr_chart ]
       	}]
	};
    
    var hr_descTab = {
    	xtype: 'fieldset',
    	title: 'Description',
    	collapsible: true,
    	collapsed : true,
    	items: [{
    		xtype : 'form',
    		html : 'abcdefg<br/>  abcdefg<br/>      abcdefg'
    	}]
    };
    
    var hr_panel = Ext.create('Ext.panel.Panel', {
    	bodyPadding: 5,
    	width : 1000,
    	items: [hr_searchTab, hr_chartTab, hr_descTab, hr_resultTab],
    	renderTo: Ext.getBody()
    });
});