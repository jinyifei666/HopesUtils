复杂的数据库查询:
//以下查询分解:
           case SMS_CONVERSATIONS:
                qb.setTables("sms, (SELECT thread_id AS group_thread_id, MAX(date)AS group_date,"
                       + "COUNT(*) AS msg_count FROM sms GROUP BY thread_id) AS groups");
                qb.appendWhere("sms.thread_id = groups.group_thread_id AND sms.date ="
                       + "groups.group_date");
                qb.setProjectionMap(sConversationProjectionMap);
                
//-----------------------------------------第一句分解查询,这个是查询出一张表,给一个别名为:msg_count ----GROUP BY:表示分组 AS:表示别名

				(SELECT thread_id AS group_thread_id, MAX(date)AS group_date,"
                       + "COUNT(*) AS msg_count FROM sms GROUP BY thread_id) AS groups");
                  1.实际为如下:     
               SELECT 
               		thread_id AS group_thread_id, MAX(date)AS group_date, COUNT(*) AS msg_count
               FROM
               		sms
               GROUP BY
               		thread_id
               		
              // 查询结果：		groups表
               group_thread_id		group_date		msg_count
               1					1359167594934	3
               2					1359167626344	2
//-------------------------------------------------qb.setProjectionMap(sConversationProjectionMap)分解出来.
        sConversationProjectionMap.put(Sms.Conversations.SNIPPET,
            "sms.body AS snippet");
        sConversationProjectionMap.put(Sms.Conversations.THREAD_ID,
            "sms.thread_id AS thread_id");
        sConversationProjectionMap.put(Sms.Conversations.MESSAGE_COUNT,
            "groups.msg_count AS msg_count");
//实际查询:这里是从两张表中去查,一张sms,一张groups
	SELECT
		sms.thread_id AS thread_id, sms.body AS snippet, groups.msg_count
	FROM
		sms,   (SELECT 
               		thread_id AS group_thread_id, MAX(date)AS group_date, COUNT(*) AS msg_count
               FROM
               		sms
               GROUP BY
               		thread_id
               )AS groups
    WHERE
    	sms.thread_id = groups.group_thread_id AND sms.date = groups.group_date
			
    	
                 // groups表查询结果：		
               group_thread_id		group_date		msg_count
               1					1359167594934	3
               2					1359167626344	2 	
    	
    	
    //总的查询结果:
    thread_id	snippet			msg_count
	1			100 + 100		3
	2			2000 + iphone5	2
	
	
	//-------------------------------
	我们需要的查询的有:id(每个会话有一个唯一id,记录给id起_id的别名,不然查询出错.) 内容,每个对话的总数,电话号码,时间
    
    SELECT
		sms.thread_id AS _id, sms.body AS snippet, groups.msg_count, sms.address AS address, sms.date AS date
	FROM
		sms,   (SELECT 
               		thread_id AS group_thread_id, MAX(date)AS group_date, COUNT(*) AS msg_count
               FROM
               		sms
               GROUP BY
               		thread_id
               )AS groups
    WHERE
    	sms.thread_id = groups.group_thread_id AND sms.date = groups.group_date
    	
   
   //最后的查询结果：
   
   _id	snippet			msg_count	address	date
	1			100 + 100		3			10010	1359167594934
	2			2000 + iphone5	2			10000	1359167626344