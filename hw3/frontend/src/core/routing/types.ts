export interface PageRoute {
    path: string;
    element: React.FC;
    authOnly?: boolean;
}
