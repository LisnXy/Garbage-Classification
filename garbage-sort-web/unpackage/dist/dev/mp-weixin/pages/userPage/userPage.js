(global['webpackJsonp'] = global['webpackJsonp'] || []).push([
    ['pages/userPage/userPage'],
    {
        /***/ 157:
            /*!******************************************************************************************************************************!*\
  !*** C:/Users/win/Desktop/repository/Garbage-Classification/garbage-sort-web/main.js?{"page":"pages%2FuserPage%2FuserPage"} ***!
  \******************************************************************************************************************************/
            /*! no static exports found */
            /***/ function (module, exports, __webpack_require__) {
                'use strict';
                /* WEBPACK VAR INJECTION */ (function (createPage) {
                    __webpack_require__(/*! uni-pages */ 5);
                    var _vue = _interopRequireDefault(
                        __webpack_require__(/*! vue */ 3),
                    );
                    var _userPage = _interopRequireDefault(
                        __webpack_require__(
                            /*! ./pages/userPage/userPage.vue */ 158,
                        ),
                    );
                    function _interopRequireDefault(obj) {
                        return obj && obj.__esModule ? obj : { default: obj };
                    }
                    wx.__webpack_require_UNI_MP_PLUGIN__ = __webpack_require__;
                    createPage(_userPage.default);
                    /* WEBPACK VAR INJECTION */
                }.call(
                    this,
                    __webpack_require__(
                        /*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 1,
                    )['createPage'],
                ));

                /***/
            },

        /***/ 158:
            /*!***********************************************************************************************************!*\
  !*** C:/Users/win/Desktop/repository/Garbage-Classification/garbage-sort-web/pages/userPage/userPage.vue ***!
  \***********************************************************************************************************/
            /*! no static exports found */
            /***/ function (module, __webpack_exports__, __webpack_require__) {
                'use strict';
                __webpack_require__.r(__webpack_exports__);
                /* harmony import */ var _userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__ =
                    __webpack_require__(
                        /*! ./userPage.vue?vue&type=template&id=88efa074&scoped=true& */ 159,
                    );
                /* harmony import */ var _userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ =
                    __webpack_require__(
                        /*! ./userPage.vue?vue&type=script&lang=js& */ 161,
                    );
                /* harmony reexport (unknown) */ for (var __WEBPACK_IMPORT_KEY__ in _userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__)
                    if (__WEBPACK_IMPORT_KEY__ !== 'default')
                        (function (key) {
                            __webpack_require__.d(
                                __webpack_exports__,
                                key,
                                function () {
                                    return _userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[
                                        key
                                    ];
                                },
                            );
                        })(__WEBPACK_IMPORT_KEY__);
                /* harmony import */ var _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_2__ =
                    __webpack_require__(
                        /*! ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/runtime/componentNormalizer.js */ 15,
                    );

                var renderjs;

                /* normalize component */

                var component = Object(
                    _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_2__[
                        'default'
                    ],
                )(
                    _userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[
                        'default'
                    ],
                    _userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                        'render'
                    ],
                    _userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                        'staticRenderFns'
                    ],
                    false,
                    null,
                    '88efa074',
                    null,
                    false,
                    _userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                        'components'
                    ],
                    renderjs,
                );

                component.options.__file = 'pages/userPage/userPage.vue';
                /* harmony default export */ __webpack_exports__['default'] =
                    component.exports;

                /***/
            },

        /***/ 159:
            /*!******************************************************************************************************************************************************!*\
  !*** C:/Users/win/Desktop/repository/Garbage-Classification/garbage-sort-web/pages/userPage/userPage.vue?vue&type=template&id=88efa074&scoped=true& ***!
  \******************************************************************************************************************************************************/
            /*! exports provided: render, staticRenderFns, recyclableRender, components */
            /***/ function (module, __webpack_exports__, __webpack_require__) {
                'use strict';
                __webpack_require__.r(__webpack_exports__);
                /* harmony import */ var _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__ =
                    __webpack_require__(
                        /*! -!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./userPage.vue?vue&type=template&id=88efa074&scoped=true& */ 160,
                    );
                /* harmony reexport (safe) */ __webpack_require__.d(
                    __webpack_exports__,
                    'render',
                    function () {
                        return _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                            'render'
                        ];
                    },
                );

                /* harmony reexport (safe) */ __webpack_require__.d(
                    __webpack_exports__,
                    'staticRenderFns',
                    function () {
                        return _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                            'staticRenderFns'
                        ];
                    },
                );

                /* harmony reexport (safe) */ __webpack_require__.d(
                    __webpack_exports__,
                    'recyclableRender',
                    function () {
                        return _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                            'recyclableRender'
                        ];
                    },
                );

                /* harmony reexport (safe) */ __webpack_require__.d(
                    __webpack_exports__,
                    'components',
                    function () {
                        return _D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_template_id_88efa074_scoped_true___WEBPACK_IMPORTED_MODULE_0__[
                            'components'
                        ];
                    },
                );

                /***/
            },

        /***/ 160:
            /*!******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!C:/Users/win/Desktop/repository/Garbage-Classification/garbage-sort-web/pages/userPage/userPage.vue?vue&type=template&id=88efa074&scoped=true& ***!
  \******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
            /*! exports provided: render, staticRenderFns, recyclableRender, components */
            /***/ function (module, __webpack_exports__, __webpack_require__) {
                'use strict';
                __webpack_require__.r(__webpack_exports__);
                /* harmony export (binding) */ __webpack_require__.d(
                    __webpack_exports__,
                    'render',
                    function () {
                        return render;
                    },
                );
                /* harmony export (binding) */ __webpack_require__.d(
                    __webpack_exports__,
                    'staticRenderFns',
                    function () {
                        return staticRenderFns;
                    },
                );
                /* harmony export (binding) */ __webpack_require__.d(
                    __webpack_exports__,
                    'recyclableRender',
                    function () {
                        return recyclableRender;
                    },
                );
                /* harmony export (binding) */ __webpack_require__.d(
                    __webpack_exports__,
                    'components',
                    function () {
                        return components;
                    },
                );
                var components;
                var render = function () {
                    var _vm = this;
                    var _h = _vm.$createElement;
                    var _c = _vm._self._c || _h;
                };
                var recyclableRender = false;
                var staticRenderFns = [];
                render._withStripped = true;

                /***/
            },

        /***/ 161:
            /*!************************************************************************************************************************************!*\
  !*** C:/Users/win/Desktop/repository/Garbage-Classification/garbage-sort-web/pages/userPage/userPage.vue?vue&type=script&lang=js& ***!
  \************************************************************************************************************************************/
            /*! no static exports found */
            /***/ function (module, __webpack_exports__, __webpack_require__) {
                'use strict';
                __webpack_require__.r(__webpack_exports__);
                /* harmony import */ var _D_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ =
                    __webpack_require__(
                        /*! -!./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./userPage.vue?vue&type=script&lang=js& */ 162,
                    );
                /* harmony import */ var _D_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default =
                    /*#__PURE__*/ __webpack_require__.n(
                        _D_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__,
                    );
                /* harmony reexport (unknown) */ for (var __WEBPACK_IMPORT_KEY__ in _D_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__)
                    if (__WEBPACK_IMPORT_KEY__ !== 'default')
                        (function (key) {
                            __webpack_require__.d(
                                __webpack_exports__,
                                key,
                                function () {
                                    return _D_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[
                                        key
                                    ];
                                },
                            );
                        })(__WEBPACK_IMPORT_KEY__);
                /* harmony default export */ __webpack_exports__['default'] =
                    _D_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_D_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_userPage_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a;

                /***/
            },

        /***/ 162:
            /*!*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!C:/Users/win/Desktop/repository/Garbage-Classification/garbage-sort-web/pages/userPage/userPage.vue?vue&type=script&lang=js& ***!
  \*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
            /*! no static exports found */
            /***/ function (module, exports, __webpack_require__) {
                'use strict';
                Object.defineProperty(exports, '__esModule', { value: true });
                exports.default = void 0; //
                //
                //
                //
                //
                //
                //
                var _default = {
                    data: function data() {
                        return {};
                    },
                    methods: {},
                    mounted: function mounted() {
                        wx.login({
                            success: function success(res) {
                                console.log(res.code);
                            },
                        });
                    },
                };
                exports.default = _default;

                /***/
            },
    },
    [[157, 'common/runtime', 'common/vendor']],
]);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/userPage/userPage.js.map
