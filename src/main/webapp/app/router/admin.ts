import { Authority } from '@/shared/security/authority';

const JhiUserManagementComponent = () => import('@/admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('@/admin/docs/docs.vue');
const JhiHealthComponent = () => import('@/admin/health/health.vue');
const JhiLogsComponent = () => import('@/admin/logs/logs.vue');
const JhiAuditsComponent = () => import('@/admin/audits/audits.vue');
const JhiMetricsComponent = () => import('@/admin/metrics/metrics.vue');
const SpiderComponent = () => import('@/admin/spider/spider.vue');
const SpiderNewComponent = () => import('@/admin/spider/spider-new.vue');
const SpiderDetailComponent = () => import('@/admin/spider/spider-detail.vue');
const SpiderDetailInfoComponent = () => import('@/admin/spider/spider-detail-info.vue');

export default [
  {
    path: '/admin/user-management',
    name: 'JhiUser',
    component: JhiUserManagementComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/new',
    name: 'JhiUserCreate',
    component: JhiUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'JhiUserEdit',
    component: JhiUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'JhiUserView',
    component: JhiUserManagementViewComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/spider',
    name: 'SpiderComponent',
    component: SpiderComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/spider/new',
    name: 'SpiderNewComponent',
    component: SpiderNewComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/spider/details/:recordId',
    name: 'SpiderDetailComponent',
    component: SpiderDetailComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/spider/details/:detailId/info',
    name: 'SpiderDetailInfoComponent',
    component: SpiderDetailInfoComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/docs',
    name: 'JhiDocsComponent',
    component: JhiDocsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/audits',
    name: 'JhiAuditsComponent',
    component: JhiAuditsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/jhi-health',
    name: 'JhiHealthComponent',
    component: JhiHealthComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/logs',
    name: 'JhiLogsComponent',
    component: JhiLogsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/jhi-metrics',
    name: 'JhiMetricsComponent',
    component: JhiMetricsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/jhi-configuration',
    name: 'JhiConfigurationComponent',
    component: JhiConfigurationComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
];
