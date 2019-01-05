$(function() {
	var baseUrl = "/springbackend";
	var listUrl = baseUrl + '/shopadmin/getproductcategorylist';
	var addUrl = baseUrl + '/shopadmin/addproductcategorys';
	var deleteUrl = baseUrl + '/shopadmin/removeproductcategory';
	getList();
	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var dataList = data.data;
								$('.category-wrap').html('');
								var tempHtml = '';
								dataList
										.map(function(item, index) {
											tempHtml += ''
													+ '<div class="row row-product-category now">'
													+ '<div class="col-33 product-category-name">'
													+ item.productCategoryName
													+ '</div>'
													+ '<div class="col-33">'
													+ item.priority
													+ '</div>'
													+ '<div class="col-33"><a href="#" class="button delete" data-id="'
													+ item.productCategoryId
													+ '">Delete</a></div>'
													+ '</div>';
										});
								$('.category-wrap').append(tempHtml);
							}
						});
	}
	$('#new')
			.click(
					function() {
						var tempHtml = '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="Category Name"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="Priority"></div>'
								+ '<div class="col-33"><a href="#" class="button delete">Delete</a></div>'
								+ '</div>';
						$('.category-wrap').append(tempHtml);
					});
	$('#submit').click(function() {
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addUrl,
			type : 'POST',
			data : JSON.stringify(productCategoryList),
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('Post success！');
					getList();
				} else {
					$.toast('Post failed！');
				}
			}
		});
	});

	$('.category-wrap').on('click', '.row-product-category.temp .delete',
			function(e) {
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();

			});
	$('.category-wrap').on('click', '.row-product-category.now .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('Sure?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							productCategoryId : target.dataset.id
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('Delete success！');
								getList();
							} else {
								$.toast('Delete failed！');
							}
						}
					});
				});
			});
});