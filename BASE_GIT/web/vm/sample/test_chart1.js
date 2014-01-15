
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
		fields : [ { name : 'regionid', mapping : 'regionid' }, 
		           { name : 'regionname', mapping : 'regionname'}
		],
		autoLoad : false,
		listeners : {
 			load : function() {
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
		autoLoad : false,
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
    		url : '/sample.do?method=chartData',
    		reader : {
    			type : 'json',
    			root : 'data'
			},
			// 확장 파라미터 설정
			extraParams: {
				stateid : "0",
				branchid : "0",
				datefrom : "0",
				dateto : "0",
				dates : "0"
			}
    	},
		fields : [ 'BRANCHID', 'HOUR', 'WORKINGMIN'],
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
			items : [
			    {
			    	xtype : 'displayfield',
			    	columnWidth : .05
			    },{
				xtype : 'container',
				columnWidth : .3,
				items : [{
					id : 'hr-search-form-state-combo',
					xtype: 'combobox',		
					fieldLabel: 'State',
					name: 'searchState',
					anchor:'95%',
					store:hr_states_store,
					displayField:'regionname',
					valueField:'regionid',
					editable : false,
					allowBlank : false,
					listeners: {
						select: function() {
							hr_branch_store.getProxy().extraParams.regionid = this.getValue();
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
                	allowBlank : false,
                	// TEST VALUE
                	//value : '2013-01-01'                	        		  
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
					format : 'Y-m-d',
					name: 'searchDateTo',
					anchor:'95%',
					editable : false,
					allowBlank : false,
					// TEST VALUE
					//value : '2013-01-10',                	        		  
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
						var values = form.getValues(); 
						if(form.isValid() && (values['searchDateFrom'] <= values['searchDateTo']) ){                	        				                	        				 
							hr_chartStore.getProxy().extraParams.stateid = values['searchState'];
             	        	hr_chartStore.getProxy().extraParams.branchid = values['searchBrach'];
							hr_chartStore.getProxy().extraParams.datefrom = values['searchDateFrom'];
							hr_chartStore.getProxy().extraParams.dateto = values['searchDateTo'];
							hr_chartStore.getProxy().extraParams.dates = values['searchDateType'];
							hr_chartStore.load();                	        				  
						} else {
							Ext.Msg.alert("Warning","Search condition is not correct. One or more field is not valid.");
						}
					}
				},{
					xtype: 'button',
					text: 'RESET',
					width : 200,
					handler: function() {
						this.up('form').getForm().reset();
						hr_chartStore.removeAll();
						Ext.getCmp('resultForm').getForm().reset();						
					}
				}]
			}]
        }, {
			xtype : 'container',
			layout : 'column',
			items : [
			    {
		    	xtype : 'displayfield',
		    	columnWidth : .05
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
    
    // 선택된 cashier 배경색 지정 
    var hr_chart_color = function(index){
    	 var value = index % 5;
         var color = ['rgb(44, 153, 201)', 
                      'rgb(213, 70, 121)', 
                      'rgb(107, 102, 255)', 
                      'rgb(49, 149, 0)', 
                      'rgb(249, 153, 0)'][value];
         return color;
    };
    
    // 차트 출력(그리드)
    var hr_workingmin_chart = Ext.create('Ext.chart.Chart', {
    	animate: true,
    	style: 'background:#fff',
    	shadow: false,
    	store: hr_chartStore,
    	width: 800,
    	height: 300,
    	axes: [{
    		type: 'Category',
    		position: 'bottom',
    		fields: ['HOUR'],
    		title: 'HOUR'
    	}, {
    		type: 'Numeric',
    		position: 'left',
    		fields: ['WORKINGMIN'],
    		label: {
    			renderer: Ext.util.Format.numberRenderer('0,0')
    		},
    		title: 'WORKINGMIN',
    		minimum: 0
    	}],
    	series: [{
            type: 'column',
            axis: 'left',
            label: {
            	display: 'insideEnd',
                field: 'WORKINGMIN',
                renderer: Ext.util.Format.numberRenderer('0'),
                orientation: 'horizontal',
                color: '#333',
                'text-anchor': 'middle'
            },
            xField: 'HOUR',
            yField: ['WORKINGMIN'],
            renderer: function(sprite, record, attr, index, store) {
            	 return Ext.apply(attr, {
                     fill: hr_chart_color(index)
                 });
            },
            listeners : {
            	itemmouseup : function(obj) {
            	}
            }
    	}]
    });
    
    var EXPWORKINGMIN = 20;//Ext.getCmp('EXPWORKINGMIN').getValue();
    var hr_workingman_chart = Ext.create('Ext.chart.Chart', {
    	autoLoad : false,
    	animate: true,
    	style: 'background:#fff',
    	shadow: false,
    	store: hr_chartStore,
    	width: 800,
    	height: 300,
    	axes: [{
    		type: 'Category',
    		position: 'bottom',
    		fields: ['HOUR'],
    		title: 'HOUR'
    	}, {
    		type: 'Numeric',
    		position: 'left',
    		fields: ['WORKINGMIN'],
    		label: {
    			renderer: Math.round(Ext.util.Format.numberRenderer('0,0') / EXPWORKINGMIN)
    		},
    		title: 'WORKINGMAN',
    		minimum: 0
    	}],
    	series: [{
            type: 'column',
            axis: 'left',
            xField: 'HOUR',
            yField: ['WORKINGMIN'],
            renderer: function(sprite, record, attr, index, store) {
            	 return Ext.apply(attr, {
                     fill: hr_chart_color(index)
                 });
            },
            listeners : {
            	itemmouseup : function(obj) {
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
    			columnWidth : .30           			   
    		},{
    			xtype : 'container',
    			columnWidth : .9,
    			layout : 'form',
    			items : [{
    				xtype : 'fieldcontainer',
    				layout : 'hbox',
    				items: [{
    					xtype : 'textfield',
    					name : 'EXPWORKINGMIN',
    					width : 50,
    					allowBlank : false,
    					id : 'EXPWORKINGMIN',
    					value : 30
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
    							hr_workingman_chart.load();
    						}
    					}
    				}]
    			}]
    		}]
    	}]
    });
            	  
    var hr_workingmin_chartTab = {
       	xtype: 'container',
       	layout : 'column',
       	items : [{
			xtype : 'displayfield',
			columnWidth : .05           			   
		},{
			xtype : 'container',
			columnWidth : .9,
			items : [ hr_workingmin_chart ]
       	}]
	};
    
    var hr_workingman_chartTab = {
       	xtype: 'container',
       	layout : 'column',
       	items : [{
			xtype : 'displayfield',
			columnWidth : .05           			   
		},{
			xtype : 'container',
			columnWidth : .9,
			items : [ hr_workingman_chart ]
       	}]
	};
    
    var hr_panel = Ext.create('Ext.panel.Panel', {
    	bodyPadding: 5,
    	width : 1000,
    	items: [hr_searchTab, hr_workingmin_chartTab, hr_resultTab, hr_workingman_chartTab],
    	renderTo: Ext.getBody()
    });
});