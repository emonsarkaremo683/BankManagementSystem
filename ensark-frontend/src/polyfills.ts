/**
 * `sockjs-client` (used by websocket.service.ts for STOMP notifications) is a
 * CommonJS/Node-oriented package that references the Node global `global` at
 * module load time. Angular's esbuild/rolldown-based builder does not
 * polyfill Node globals for the browser the way older Webpack configs did,
 * so without this the whole app fails to bootstrap with
 * "ReferenceError: global is not defined" as soon as anything imports the
 * websocket service. This file is registered as a dedicated Angular
 * `polyfills` entry point (see angular.json) specifically so it's evaluated
 * before the rest of the app's module graph, including sockjs-client.
 */
(window as any).global = window;
