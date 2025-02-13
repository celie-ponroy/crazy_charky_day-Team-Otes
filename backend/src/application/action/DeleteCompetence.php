<?php

namespace charly\application\action;
use charly\core\service\interfaces\CompetenceServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class DeleteCompetence extends AbstractAction
{
    protected CompetenceServiceInterface $competenceService;

    public function __construct(CompetenceServiceInterface $competenceService)
    {
        $this->competenceService = $competenceService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $id = $args['id'];

            $this->competenceService->deleteCompetence($id);

            $responseData = [
                'success' => true,
                'message' => 'Suppression enregistrée avec succès'
            ];

            $rs->getBody()->write(json_encode($responseData));
            return $rs->withHeader('Content-Type', 'application/json')->withStatus(201);
        } catch (\Exception $e) {
            return $this->respondWithError($rs, $e->getMessage(), 500);
        }
    }

    private function respondWithError(ResponseInterface $response, string $message, int $status): ResponseInterface
    {
        $responseData = [
            'status' => $status,
            'error' => $message
        ];

        $response->getBody()->write(json_encode($responseData, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));

        return $response->withHeader('Content-Type', 'application/json')->withStatus($status);
    }
}